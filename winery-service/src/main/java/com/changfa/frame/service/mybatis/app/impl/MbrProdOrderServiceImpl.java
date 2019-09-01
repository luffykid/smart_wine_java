package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrProdOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.SettingUtils;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("mbrProdOrderServiceImpl")
public class MbrProdOrderServiceImpl extends BaseServiceImpl<MbrProdOrder, Long> implements MbrProdOrderService {

    @Autowired
    private MbrProdOrderMapper mbrProdOrderMapper;

    @Autowired
    private MbrProdOrderRecordMapper mbrProdOrderRecordMapper;

    @Autowired
    private MbrBillRecordMapper mbrBillRecordMapper;

    @Autowired
    private MbrProdOrderItemMapper mbrProdOrderItemMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MbrIntegralRecordMapper mbrIntegralRecordMapper;

    @Autowired
    private ProdSkuMapper prodSkuMapper;

    /**
     * 处理会员商品订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    @Transactional
    @Override
    public void handleNotifyOfProdOrder(String outTradeNo, String transactionId, Date payDate) {
        // 根据订单号查询订单信息
        MbrProdOrder dbOrder = mbrProdOrderMapper.getByOrderNo(outTradeNo);
        if (dbOrder == null) {
            return;
        }

        // 如果订单已经处理则不在处理订单
        if (dbOrder.getOrderStatus().equals(MbrProdOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue())) {
            return;
        }

        // 更新商品订单
        MbrProdOrder updateOrder = new MbrProdOrder();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setOrderStatus(MbrProdOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        updateOrder.setTransactionNo(transactionId);
        updateOrder.setPayDate(payDate);
        updateOrder.setModifyDate(new Date());
        mbrProdOrderMapper.update(updateOrder);

        // 插入订单记录
        MbrProdOrderRecord saveOrderRecord = new MbrProdOrderRecord();
        saveOrderRecord.setId(IDUtil.getId());
        saveOrderRecord.setMbrProdOrderId(dbOrder.getId());
        saveOrderRecord.setOrderRemark("订单支付成功");
        saveOrderRecord.setOrderStatus(MbrProdOrderRecord.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        saveOrderRecord.setCreateDate(new Date());
        saveOrderRecord.setModifyDate(new Date());
        mbrProdOrderRecordMapper.save(saveOrderRecord);

        // 插入会员账户流水信息
        MbrBillRecord mbrBillRecord = new MbrBillRecord();
        mbrBillRecord.setId(IDUtil.getId());
        mbrBillRecord.setPkId(dbOrder.getId());
        mbrBillRecord.setWineryId(dbOrder.getWineryId());
        mbrBillRecord.setSignType(0);
        mbrBillRecord.setBillType(MbrBillRecord.BILL_TYPE_ENUM.PROD_CUSTOM.getValue());
        mbrBillRecord.setBillRemark("商品消费");
        mbrBillRecord.setBillAmt(dbOrder.getPayRealAmt());
        mbrBillRecord.setCreateDate(new Date());
        mbrBillRecord.setModifyDate(new Date());
        mbrBillRecordMapper.save(mbrBillRecord);

        // 批量更新更新对应SKU销售数量
        MbrProdOrderItem mbrProdOrderItem = new MbrProdOrderItem();
        mbrProdOrderItem.setMbrProdOrderId(dbOrder.getId());
        List<MbrProdOrderItem> mbrProdOrderItems = mbrProdOrderItemMapper.selectList(mbrProdOrderItem);
        List<ProdSku> prodSkus = new ArrayList();
        for (MbrProdOrderItem prodOrderItem : mbrProdOrderItems) {
            ProdSku prodSku = new ProdSku();
            prodSku.setId(prodOrderItem.getProdSkuId());
            prodSku.setSellCnt(prodOrderItem.getProdSkuCnt());
            prodSkus.add(prodSku);
        }
        prodSkuMapper.updateSellCnt(prodSkus);

        // 邀请返现
        handleInviteReturn(dbOrder);

        // 消费送积分
        handleConsumeIntegral(dbOrder);

        // 如果是积分+金额支付方式，扣减积分 todo 此处积分扣减在回调延迟情况下会出现负积分，后期可以加预扣除积分操作
        if (dbOrder.getPayMode().equals(MbrProdOrder.PAY_MODE_ENUM.WX_MINI_INTEGRAL_MODE.getValue())) {
            handleDeductIntegral(dbOrder);
        }

    }

    /**
     * 依据当前会员ID处理积分
     *
     * @param mbrProdOrder 会员订单
     */
    private void handleDeductIntegral(MbrProdOrder mbrProdOrder) {
        // 如果会员没有父级，不用处理邀请返现
        Member curMbr = memberMapper.getById(mbrProdOrder.getMbrId());

        // 插入积分记录
        MbrIntegralRecord integralRecord = new MbrIntegralRecord();
        integralRecord.setId(IDUtil.getId());
        integralRecord.setWineryId(mbrProdOrder.getWineryId());
        integralRecord.setMbrId(mbrProdOrder.getMbrId());
        integralRecord.setActionType(MbrIntegralRecord.ACTION_TYPE_ENUM.PROD_CUSTOM.getValue());
        integralRecord.setPkId(mbrProdOrder.getId());
        integralRecord.setSignType(0);

//        // 计算积分
//        BigDecimal payRealAmt = mbrProdOrder.getPayIntegralCnt();
////        BigDecimal divide = payRealAmt.divide(new BigDecimal(integralScale));
//        BigDecimal integral = divide.setScale(2, BigDecimal.ROUND_HALF_DOWN);
//        integralRecord.setIntegralValue(integral);
//
//        // 计算操作后积分
//        BigDecimal preIntegral = curMbr.getTotalIntegral();
//        BigDecimal lastIntegral = preIntegral.add(integral).setScale(2, BigDecimal.ROUND_HALF_UP);
//        integralRecord.setLatestPoint(lastIntegral);
//        integralRecord.setCreateDate(new Date());
        integralRecord.setModifyDate(new Date());
        mbrIntegralRecordMapper.save(integralRecord);

        // 更新会员积分
        Member updateMbr = new Member();
        updateMbr.setId(curMbr.getId());
//        updateMbr.setTotalIntegral(lastIntegral);
        updateMbr.setModifyDate(new Date());
        memberMapper.update(updateMbr);
    }

    /**
     * 依据当前会员ID处理积分
     *
     * @param mbrProdOrder 会员订单
     */
    private void handleConsumeIntegral(MbrProdOrder mbrProdOrder) {
        // 如果会员没有父级，不用处理邀请返现
        String integralScale = SettingUtils.get().getConsumeAmtOfIntegral();
        Member curMbr = memberMapper.getById(mbrProdOrder.getMbrId());

        // 插入积分记录
        MbrIntegralRecord integralRecord = new MbrIntegralRecord();
        integralRecord.setId(IDUtil.getId());
        integralRecord.setWineryId(mbrProdOrder.getWineryId());
        integralRecord.setMbrId(mbrProdOrder.getMbrId());
        integralRecord.setActionType(MbrIntegralRecord.ACTION_TYPE_ENUM.PROD_CUSTOM.getValue());
        integralRecord.setPkId(mbrProdOrder.getId());
        integralRecord.setSignType(1);

        // 计算积分
        BigDecimal payRealAmt = mbrProdOrder.getPayRealAmt();
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

    /**
     * 依据当前会员ID处理返现
     *
     * @param mbrProdOrder 会员订单
     */
    private void handleInviteReturn(MbrProdOrder mbrProdOrder) {
        // 如果会员没有父级，不用处理邀请返现
        String inviteReturnScale = SettingUtils.get().getInviteReturnScale();
        Member curMbr = memberMapper.getById(mbrProdOrder.getMbrId());
        Member parenMbr = memberMapper.getById(curMbr.getMarketPid());
        if (parenMbr == null) {
            return;
        }

        // 插入返现会员账户记录
        MbrBillRecord mbrBillRecord = new MbrBillRecord();
        mbrBillRecord.setId(IDUtil.getId());
        mbrBillRecord.setPkId(mbrProdOrder.getId());
        mbrBillRecord.setWineryId(mbrProdOrder.getWineryId());
        mbrBillRecord.setSignType(0);
        mbrBillRecord.setBillType(MbrBillRecord.BILL_TYPE_ENUM.INVITE_RETURN.getValue());
        mbrBillRecord.setBillRemark("邀请返现");
        // 设置返现金额
        BigDecimal payRealAmt = mbrProdOrder.getPayRealAmt();
        BigDecimal returnAmt = payRealAmt.multiply(new BigDecimal(inviteReturnScale));
        returnAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        mbrBillRecord.setBillAmt(returnAmt);

        mbrBillRecord.setCreateDate(new Date());
        mbrBillRecord.setModifyDate(new Date());
        mbrBillRecordMapper.save(mbrBillRecord);

        // 更新会员账户余额
        Member updateMbr = new Member();
        updateMbr.setId(parenMbr.getId());
        updateMbr.setAcctBalance(parenMbr.getAcctBalance().add(returnAmt));
        updateMbr.setInviteReturnAmt(parenMbr.getInviteReturnAmt().add(returnAmt));
        updateMbr.setModifyDate(new Date());
        memberMapper.update(parenMbr);
    }
}
