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
    private MbrStoreOrderRecordMapper mbrStoreOrderRecordMapper;
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
        //四个id
        mbrStoreOrder.setId(IDUtil.getId());
        mbrStoreOrder.setMbrId(mbrId);
        mbrStoreOrder.setWineryId(wineryId);
        mbrStoreOrder.setWineCellarActivityId(activityId);

        mbrStoreOrder.setProdTotalCnt(prodTotalCnt);//数量
        mbrStoreOrder.setPayTotalAmt(prodSku.getMbrPrice().multiply(new BigDecimal(prodTotalCnt)));//获取会员价格插入到总支付金额
        mbrStoreOrder.setPayRealAmt(mbrStoreOrder.getPayTotalAmt());

        mbrStoreOrder.setPayIntegralCnt(new BigDecimal(0));//支付积分

        mbrStoreOrder.setTotalOrgWeight(prodSku.getSkuWeight().multiply(new BigDecimal(prodTotalCnt)));
        mbrStoreOrder.setTotalOrgCapacity(prodSku.getSkuCapacity().multiply(new BigDecimal(prodTotalCnt)));

        mbrStoreOrder.setTotalIncreaseWeight(new BigDecimal(0));
        mbrStoreOrder.setTotalIncreaseCapacity(new BigDecimal(0));

        mbrStoreOrder.setTotalRemainWeight(mbrStoreOrder.getTotalOrgWeight());
        mbrStoreOrder.setTotalRemainCapacity(mbrStoreOrder.getTotalOrgCapacity());

        mbrStoreOrder.setPayMode(MbrStoreOrder.PAY_MODE_ENUM.WX_MINI_MODE.getValue());//支付方式： 1： 微信支付
        mbrStoreOrder.setOrderStatus(MbrStoreOrder.ORDER_STATUS_ENUM.PAY_NOT.getValue());//1：未支付（已生成预支付ID）

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

        mbrStoreOrderItem.setIsIntegral(Boolean.FALSE);
        mbrStoreOrderItem.setIntegralAmt(new BigDecimal(0));
        mbrStoreOrderItem.setIntegralCnt(new BigDecimal(0));
        mbrStoreOrderItem.setSkuWeight(prodSku.getSkuWeight());
        mbrStoreOrderItem.setSkuCapacity(prodSku.getSkuCapacity());
        mbrStoreOrderItem.setCreateDate(new Date());
        mbrStoreOrderItemMapper.save(mbrStoreOrderItem);



        //3.保存MbrStoreOrderRecord
        MbrStoreOrderRecord mbrStoreOrderRecord = new MbrStoreOrderRecord();
        mbrStoreOrderRecord.setId(IDUtil.getId());
        mbrStoreOrderRecord.setMbrStoreOrderId(mbrStoreOrder.getId());
        mbrStoreOrderRecord.setOrderStatus(mbrStoreOrder.getOrderStatus());
        mbrStoreOrderRecord.setOrderRemark("");
        mbrStoreOrderRecord.setCreateDate(new Date());
        mbrStoreOrderRecordMapper.save(mbrStoreOrderRecord);

        // 4.微信支付预下单
        Map<String, Object> returnMap = WeChatPayUtil.unifiedOrderOfWxMini(orderNo,
                mbrStoreOrder.getPayTotalAmt(), member.getOpenId(),
                "/paymentNotify/async_notify/MBR_STORE_ORDER.jhtml", "会员储酒订单", request);
        return returnMap;
    }
}
