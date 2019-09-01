package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.ProdSkuMapper;
import com.changfa.frame.mapper.app.ProdSkuMbrPriceMapper;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.WineryWineProdService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("wineryWineProdServiceImpl")
public class WineryWineProdServiceImpl extends BaseServiceImpl<WineryWineProd, Long>
                                        implements WineryWineProdService {

    @Autowired
    private ProdSkuMbrPriceMapper prodSkuMbrPriceMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;



    @Override
    public List<WineryWineProd> getAllByWineryWineId(Member member, Long wineryWineId) {

        WineryWineProd queryForWineryWineId = new WineryWineProd();
        queryForWineryWineId.setWineryWineId(wineryWineId);

        if (member == null)
            throw new NullPointerException("member must not be null!");

        List<WineryWineProd> wineryWineProds = this.selectList(queryForWineryWineId);

        wineryWineProds.forEach(wineryWineProd -> {

            ProdSkuMbrPrice price = getMbrPrice(member, wineryWineProd);
            wineryWineProd.setMbrPrice(price.getMbrLevelPrice());

        });

        return wineryWineProds;
    }

    @Override
    public List<ProdSku> getAllSkuWithMbrPrice(Member member, Long wineryWineProdId) {

        WineryWineProd prod = this.getById(wineryWineProdId);

        if (prod == null)
            throw new IllegalArgumentException("the wineryWineProdId don't exsit!");

        List<ProdSku> prodSkus = getEnabledProdSkus(prod);

        prodSkus.forEach(prodSku -> {

            ProdSkuMbrPrice price = getMbrPriceUnderTheMember(member, prodSku);
            prodSku.setMbrPrice(price.getMbrLevelPrice());

        });

        return prodSkus;

    }

    private ProdSkuMbrPrice getMbrPrice(Member member, WineryWineProd wineryWineProd) {

        ProdSku prodSku = getFirstEnabledProdSku(wineryWineProd);

        ProdSkuMbrPrice price = getMbrPriceUnderTheMember(member, prodSku);

        return price;

    }

    private ProdSkuMbrPrice getMbrPriceUnderTheMember(Member member, ProdSku prodSku) {

        return prodSkuMbrPriceMapper.getBySkuId(prodSku.getId())
                                                        .stream()
                                                        .filter(mbrPrice
                                                                ->
                                                                mbrPrice.getMbrLevelId() == member.getMbrLevelId())
                                                        .collect(Collectors.toList()).get(0);
    }

    private ProdSku getFirstEnabledProdSku(WineryWineProd wineryWineProd) {
        return prodSkuMapper.getByProdId(wineryWineProd.getProdId())
                                            .stream()
                                            .filter(sku
                                                    ->
                                                    sku.getSkuStatus() == ProdSku.SKU_STATUS_ENUM.YSJ.getValue())
                                            .filter(sku
                                                    ->
                                                    sku.getDel() == false)
                                            .sorted(Comparator.comparing(ProdSku::getSort))
                                            .collect(Collectors.toList()).get(0);
    }

    private List<ProdSku> getEnabledProdSkus(WineryWineProd wineryWineProd) {
        return prodSkuMapper.getByProdId(wineryWineProd.getProdId())
                .stream()
                .filter(sku
                        ->
                        sku.getSkuStatus() == ProdSku.SKU_STATUS_ENUM.YSJ.getValue())
                .filter(sku
                        ->
                        sku.getDel() == false)
                .sorted(Comparator.comparing(ProdSku::getSort))
                .collect(Collectors.toList());
    }

}
