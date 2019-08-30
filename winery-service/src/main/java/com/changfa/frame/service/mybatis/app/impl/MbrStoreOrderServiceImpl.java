package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.MbrStoreOrderItemMapper;
import com.changfa.frame.mapper.app.MbrStoreOrderMapper;
import com.changfa.frame.mapper.app.ProdSkuMapper;
import com.changfa.frame.model.app.MbrStoreOrder;
import com.changfa.frame.model.app.MbrStoreOrderItem;
import com.changfa.frame.model.app.ProdSku;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("mbrStoreOrderServiceImpl")
public class MbrStoreOrderServiceImpl extends BaseServiceImpl<MbrStoreOrder, Long> implements MbrStoreOrderService {

    @Autowired
    private MbrStoreOrderMapper mbrStoreOrderMapper;
    @Autowired
    private MbrStoreOrderItemMapper mbrStoreOrderItemMapper;
    @Autowired
    private ProdSkuMapper prodSkuMapper;
    /**
     * 我的储酒
     * @param mbrId
     * @return
     */
    @Override
    public List<MbrStoreOrder> getStoreList(Long mbrId) {

        return mbrStoreOrderMapper.selectStoreList(mbrId);
    }

    @Override
    public List<MbrStoreOrderItem> getMbrStoreOrderItemByStoreId(Long mbrStoreOrderId) {
        MbrStoreOrderItem mbrStoreOrderItem = new MbrStoreOrderItem();
        mbrStoreOrderItem.setMbrStoreOrderId(mbrStoreOrderId);
        List<MbrStoreOrderItem> list = mbrStoreOrderItemMapper.selectList(mbrStoreOrderItem);
        return list;
    }
    /**
     * 生成储酒订单
     *
     * @return
     */
    @Override
    public void buildOrder(Long wineryId, Long mbrId, Long dId, Long sId, Integer prodTotalCnt) {
        ProdSku prodSku = prodSkuMapper.getById(sId);
        prodTotalCnt = prodTotalCnt == null ? 1 : prodTotalCnt;
        MbrStoreOrder mbrStoreOrder = new MbrStoreOrder();
        mbrStoreOrder.setId(IDUtil.getId());
        mbrStoreOrder.setMbrId(mbrId);
        mbrStoreOrder.setWineryId(wineryId);
        mbrStoreOrder.setWineCellarActivityId(dId);
        mbrStoreOrder.setProdTotalCnt(prodTotalCnt);
        mbrStoreOrder.setPayTotalAmt(prodSku.getSkuSellPrice().multiply(new BigDecimal(prodTotalCnt)));
        mbrStoreOrder.setPayRealAmt(mbrStoreOrder.getPayTotalAmt());
        mbrStoreOrder.setPayIntegralCnt(0L);
        mbrStoreOrder.setTotalOrgWeight(prodSku.getSkuWeight().multiply(new BigDecimal(prodTotalCnt)));
        mbrStoreOrder.setTotalOrgCapacity(prodSku.getSkuCapacity().multiply(new BigDecimal(prodTotalCnt)));
        mbrStoreOrder.setTotalIncreaseWeight(new BigDecimal(0));
        mbrStoreOrder.setTotalIncreaseCapacity(new BigDecimal(0));
        mbrStoreOrder.setTotalRemainWeight(mbrStoreOrder.getTotalOrgWeight());
        mbrStoreOrder.setTotalRemainCapacity(mbrStoreOrder.getTotalOrgCapacity());
        mbrStoreOrder.setOrderNo(OrderNoUtil.get());
        mbrStoreOrder.setCreateDate(new Date());
        mbrStoreOrderMapper.save(mbrStoreOrder);
        MbrStoreOrderItem mbrStoreOrderItem = new MbrStoreOrderItem();
        mbrStoreOrderItem.setId(IDUtil.getId());
        mbrStoreOrderItem.setMbrStoreOrderId(mbrStoreOrder.getId());
        mbrStoreOrderItem.setWineryId(wineryId);
        mbrStoreOrderItem.setMbrId(mbrId);
        mbrStoreOrderItem.setProdSkuId(sId);
        mbrStoreOrderItem.setProdSkuCnt(prodTotalCnt);
        mbrStoreOrderItem.setSkuName(prodSku.getSkuName());
        mbrStoreOrderItem.setSkuMarketPrice(prodSku.getSkuMarketPrice());
        mbrStoreOrderItem.setSkuSellPrice(prodSku.getSkuSellPrice());
       // mbrStoreOrderItem.setSkuMbrPrice();

    }
}
