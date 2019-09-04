package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrStoreOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.SettingUtils;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

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
    private ProdSkuMbrPriceMapper prodSkuMbrPriceMapper;

    @Autowired
    private MbrBillRecordMapper mbrBillRecordMapper;

    @Autowired
    private WineCellarActivityMapper wineCellarActivityMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MbrIntegralRecordMapper mbrIntegralRecordMapper;
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

        ProdSkuMbrPrice entity = new ProdSkuMbrPrice();
        entity.setProdSkuId(skuId);
        List<ProdSkuMbrPrice> prodSkuMbrPriceList = prodSkuMbrPriceMapper.selectList(entity);

        for(ProdSkuMbrPrice prodSkuMbrPrice:prodSkuMbrPriceList){
            if(prodSkuMbrPrice.getMbrLevelId()== member.getMbrLevelId()){
                prodSku.setMbrPrice(prodSkuMbrPrice.getMbrLevelPrice());
            }
        }
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
        mbrStoreOrderRecord.setOrderRemark("储酒订单预下单");
        mbrStoreOrderRecord.setCreateDate(new Date());
        mbrStoreOrderRecordMapper.save(mbrStoreOrderRecord);
        // 4.返回订单号和金额 用于微信预下单
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("orderNo",orderNo);
        returnMap.put("payTotalAmt",mbrStoreOrder.getPayTotalAmt());

        return returnMap;
    }

    /**
     * 处理会员储酒订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    @Transactional
    @Override
    public void handleNotifyOfStoreOrder(String outTradeNo, String transactionId, Date payDate) {
        // 根据订单号查询订单信息
        MbrStoreOrder dbOrder = mbrStoreOrderMapper.getByOrderNo(outTradeNo);
        if (dbOrder == null) {
            return;
        }

        // 如果订单已经处理则不在处理订单
        if (dbOrder.getOrderStatus().equals(MbrStoreOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue())) {
            return;
        }

        // 更新储酒订单
        MbrStoreOrder updateOrder = new MbrStoreOrder();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setOrderStatus(MbrStoreOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        updateOrder.setTransactionNo(transactionId);
        updateOrder.setModifyDate(new Date());
        mbrStoreOrderMapper.update(updateOrder);

        // 插入订单记录
        MbrStoreOrderRecord saveOrderRecord = new MbrStoreOrderRecord();
        saveOrderRecord.setId(IDUtil.getId());
        saveOrderRecord.setMbrStoreOrderId(dbOrder.getId());
        saveOrderRecord.setOrderRemark("订单支付成功");
        saveOrderRecord.setOrderStatus(MbrStoreOrderRecord.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        saveOrderRecord.setCreateDate(new Date());
        saveOrderRecord.setModifyDate(new Date());
        mbrStoreOrderRecordMapper.save(saveOrderRecord);

        // 插入会员账户流水信息
        MbrBillRecord mbrBillRecord = new MbrBillRecord();
        mbrBillRecord.setId(IDUtil.getId());
        mbrBillRecord.setPkId(dbOrder.getId());
        mbrBillRecord.setWineryId(dbOrder.getWineryId());
        mbrBillRecord.setSignType(0);
        mbrBillRecord.setBillType(MbrBillRecord.BILL_TYPE_ENUM.STORE_CUSTOM.getValue());
        mbrBillRecord.setBillRemark("储酒消费");
        mbrBillRecord.setBillAmt(dbOrder.getPayRealAmt());
        mbrBillRecord.setCreateDate(new Date());
        mbrBillRecord.setModifyDate(new Date());
        mbrBillRecordMapper.save(mbrBillRecord);

        // 批量更新更新对应SKU销售数量
        MbrStoreOrderItem mbrStoreOrderItem = new MbrStoreOrderItem();
        mbrStoreOrderItem.setMbrStoreOrderId(dbOrder.getId());
        List<MbrStoreOrderItem> mbrStoreOrderItems = mbrStoreOrderItemMapper.selectList(mbrStoreOrderItem);
        List<ProdSku> prodSkus = new ArrayList();
        for (MbrStoreOrderItem prodOrderItem : mbrStoreOrderItems) {
            ProdSku prodSku = new ProdSku();
            prodSku.setId(prodOrderItem.getProdSkuId());
            prodSku.setSellCnt(prodOrderItem.getProdSkuCnt());
            prodSkus.add(prodSku);
        }
        prodSkuMapper.updateSellCnt(prodSkus);

        // 消费送积分
        handleConsumeIntegral(dbOrder);


    }
    /**
     * 依据当前会员ID处理积分
     *
     * @param mbrStoreOrder 会员订单
     */
    private void handleConsumeIntegral(MbrStoreOrder mbrStoreOrder) {
        // 如果会员没有父级，不用处理邀请返现
        String integralScale = SettingUtils.get().getConsumeAmtOfIntegral();
        Member curMbr = memberMapper.getById(mbrStoreOrder.getMbrId());

        // 插入积分记录
        MbrIntegralRecord integralRecord = new MbrIntegralRecord();
        integralRecord.setId(IDUtil.getId());
        integralRecord.setWineryId(mbrStoreOrder.getWineryId());
        integralRecord.setMbrId(mbrStoreOrder.getMbrId());
        integralRecord.setActionType(MbrIntegralRecord.ACTION_TYPE_ENUM.STORE_CUSTOM.getValue());
        integralRecord.setPkId(mbrStoreOrder.getId());
        integralRecord.setSignType(1);

        // 计算积分
        BigDecimal payRealAmt = mbrStoreOrder.getPayRealAmt();
        BigDecimal divide = payRealAmt.divide(new BigDecimal(integralScale));
        BigDecimal integral = divide.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        integralRecord.setIntegralValue(integral);

        // 计算操作后积分
        BigDecimal preIntegral = curMbr.getTotalIntegral();
        BigDecimal lastIntegral = preIntegral.add(integral).setScale(2, BigDecimal.ROUND_HALF_UP);
        integralRecord.setLatestPoint(lastIntegral);
        integralRecord.setCreateDate(new Date());
        integralRecord.setModifyDate(new Date());
        mbrIntegralRecordMapper.save(integralRecord);

        // 更新会员积分
        Member updateMbr = new Member();
        updateMbr.setId(curMbr.getId());
        updateMbr.setTotalIntegral(lastIntegral);
        updateMbr.setModifyDate(new Date());
        memberMapper.update(updateMbr);
    }
}
