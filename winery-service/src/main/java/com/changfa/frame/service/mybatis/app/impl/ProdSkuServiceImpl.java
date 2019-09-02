package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.ProdMapper;
import com.changfa.frame.mapper.app.ProdSkuMapper;
import com.changfa.frame.mapper.app.ProdSkuMbrPriceMapper;
import com.changfa.frame.model.app.Prod;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.model.app.ProdSkuMbrPrice;
import com.changfa.frame.service.mybatis.app.ProdSkuService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称:prodSkuServiceImpl
 * 类描述:商品Sku管理Service实现
 * 创建人:WY
 * 创建时间:2019/8/23 14:31
 * Version 1.0
 */

@Service("prodSkuServiceImpl")
public class ProdSkuServiceImpl extends BaseServiceImpl<ProdSku, Long> implements ProdSkuService {
    private static Logger log = LoggerFactory.getLogger(ProdSkuServiceImpl.class);
    @Autowired
    private ProdSkuMapper prodSkuMapper;

    @Autowired
    private ProdSkuMbrPriceMapper prodSkuMbrPriceMapper;

    @Autowired
    private ProdMapper prodMapper;
    @Override
    public ProdSku getProSkuWithMbrPriceById(Long id, Long mbrLevelId) {
        ProdSku prodSku = prodSkuMapper.getById(id);

        ProdSkuMbrPrice entity = new ProdSkuMbrPrice();
        entity.setProdSkuId(id);
        List<ProdSkuMbrPrice> prodSkuMbrPriceList = prodSkuMbrPriceMapper.selectList(entity);

        for(ProdSkuMbrPrice prodSkuMbrPrice:prodSkuMbrPriceList){
            if(prodSkuMbrPrice.getMbrLevelId()== mbrLevelId){
                prodSku.setMbrPrice(prodSkuMbrPrice.getMbrLevelPrice());
            }
        }
        Prod prod = prodMapper.getById(prodSku.getProdId());
        prodSku.setProdImg(prod.getListImg());
        return prodSku;
    }
}
