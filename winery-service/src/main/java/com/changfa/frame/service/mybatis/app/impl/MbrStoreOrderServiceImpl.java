package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.core.weChat.WeChatPayUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private ProdMapper prodMapper;

    @Autowired
    private WineCellarActivityMapper wineCellarActivityMapper;
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
    @Transactional()
    public Map<String, Object> buildStoreOrder(Long activityId, Long skuId,Integer prodTotalCnt,Member member,HttpServletRequest request) {

        ProdSku prodSku = prodSkuMapper.getById(skuId);
        WineCellarActivity wineCellarActivity = wineCellarActivityMapper.getById(activityId);
        Long wineryId =wineCellarActivity.getWineryId();
        String orderNo = OrderNoUtil.get();
        Long mbrId = member.getId();

        prodTotalCnt = prodTotalCnt == null ? 1 : prodTotalCnt;
        //1.保存MbrStoreOrder
        MbrStoreOrder mbrStoreOrder = new MbrStoreOrder();
        mbrStoreOrder.setId(IDUtil.getId());
        mbrStoreOrder.setMbrId(mbrId);
        mbrStoreOrder.setWineryId(wineryId);
        mbrStoreOrder.setWineCellarActivityId(activityId);
        mbrStoreOrder.setProdTotalCnt(prodTotalCnt);
        mbrStoreOrder.setPayTotalAmt(prodSku.getMbrPrice().multiply(new BigDecimal(prodTotalCnt)));//获取会员价格插入到总支付金额
        mbrStoreOrder.setPayRealAmt(mbrStoreOrder.getPayTotalAmt());
        mbrStoreOrder.setPayIntegralCnt(new BigDecimal(0));
        mbrStoreOrder.setTotalOrgWeight(prodSku.getSkuWeight().multiply(new BigDecimal(prodTotalCnt)));
        mbrStoreOrder.setTotalOrgCapacity(prodSku.getSkuCapacity().multiply(new BigDecimal(prodTotalCnt)));
        mbrStoreOrder.setTotalIncreaseWeight(new BigDecimal(0));
        mbrStoreOrder.setTotalIncreaseCapacity(new BigDecimal(0));
        mbrStoreOrder.setTotalRemainWeight(mbrStoreOrder.getTotalOrgWeight());
        mbrStoreOrder.setTotalRemainCapacity(mbrStoreOrder.getTotalOrgCapacity());
        mbrStoreOrder.setPayMode(1);//支付方式： 1： 微信支付
        mbrStoreOrder.setOrderStatus(1);//1：未支付（已生成预支付ID）
        mbrStoreOrder.setOrderNo(orderNo);
        mbrStoreOrder.setCreateDate(new Date());
        mbrStoreOrder.setIsNow(wineCellarActivity.getIsNow());
        mbrStoreOrder.setTakeDate(wineCellarActivity.getTakeDate());
        mbrStoreOrderMapper.save(mbrStoreOrder);
        //2.保存MbrStoreOrder
        MbrStoreOrderItem mbrStoreOrderItem = new MbrStoreOrderItem();
        mbrStoreOrderItem.setId(IDUtil.getId());
        mbrStoreOrderItem.setMbrStoreOrderId(mbrStoreOrder.getId());
        mbrStoreOrderItem.setWineryId(wineryId);
        mbrStoreOrderItem.setMbrId(mbrId);
        mbrStoreOrderItem.setProdSkuId(skuId);
        mbrStoreOrderItem.setProdSkuCnt(prodTotalCnt);
        mbrStoreOrderItem.setSkuName(prodSku.getSkuName());
        mbrStoreOrderItem.setSkuMarketPrice(prodSku.getSkuMarketPrice());
        mbrStoreOrderItem.setSkuSellPrice(prodSku.getSkuSellPrice());
        mbrStoreOrderItem.setSkuMbrPrice(prodSku.getMbrPrice());
        //3.保存MbrStoreOrderRecord

        // 4.微信支付预下单
        Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(orderNo,
                mbrStoreOrder.getPayTotalAmt(), member.getOpenId(),
                "/paymentNotify/async_notify/MBR_STORE_ORDER.jhtml", "会员储酒订单", request);
        return returnMap;
    }
}
