package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrRechargeOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
import com.changfa.frame.service.mybatis.common.SettingUtils;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("mbrRechargeOrderServiceImpl")
public class MbrRechargeOrderServiceImpl extends BaseServiceImpl<MbrRechargeOrder, Long> implements MbrRechargeOrderService {

    @Autowired
    private MbrRechargeOrderMapper mbrRechargeOrderMapper;
    @Autowired
    private MbrRechargeOrderRecordMapper mbrRechargeOrderRecordMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MbrBillRecordMapper mbrBillRecordMapper;

    @Autowired
    private MbrIntegralRecordMapper mbrIntegralRecordMapper;
    /**
     * 会员新建订单
     * @param mbrId
     * @param wineryId
     * @param payAmt

     */
    @Transactional
    @Override
    public Map<String, Object> unifiedOrder(Long mbrId, Long wineryId, BigDecimal payAmt) {
        //1.获取相关信息
        String orderNo = OrderNoUtil.get();

        //2.保存会员充值订单
        MbrRechargeOrder mbrRechargeOrder = new MbrRechargeOrder();
        mbrRechargeOrder.setId(IDUtil.getId());
        mbrRechargeOrder.setMbrId(mbrId);
        mbrRechargeOrder.setWineryId(wineryId);
        mbrRechargeOrder.setPayTotalAmt(payAmt);
        mbrRechargeOrder.setPayRealAmt(payAmt);
        mbrRechargeOrder.setOrderStatus(MbrRechargeOrder.ORDER_STATUS_ENUM.PAY_NOT.getValue());//支付状态 1.未支付
        mbrRechargeOrder.setOrderNo(orderNo);
        mbrRechargeOrder.setCreateDate(new Date());
        mbrRechargeOrder.setModifyDate(mbrRechargeOrder.getCreateDate());
        mbrRechargeOrderMapper.save(mbrRechargeOrder);

        //3.插入会员充值订单记录
        MbrRechargeOrderRecord mbrRechargeOrderRecord = new MbrRechargeOrderRecord();
        mbrRechargeOrderRecord.setId(IDUtil.getId());
        mbrRechargeOrderRecord.setMbrRechargeOrderId(mbrRechargeOrder.getId());
        mbrRechargeOrderRecord.setOrderStatus(MbrRechargeOrderRecord.ORDER_STATUS_ENUM.PAY_NOT.getValue());
        mbrRechargeOrderRecord.setCreateDate(new Date());
        mbrRechargeOrderRecord.setModifyDate(mbrRechargeOrderRecord.getCreateDate());
        mbrRechargeOrderRecordMapper.save(mbrRechargeOrderRecord);

        //4.// 4.返回订单号,用于微信预下单
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("orderNo",orderNo);
        return returnMap;
    }

    /**
     * 修改订单状态
     * @param id
     * @param orderStatus
     */
    @Transactional
    @Override
    public void update(Long id, Integer orderStatus) {
        MbrRechargeOrder mbrRechargeOrder = mbrRechargeOrderMapper.getById(id);
        mbrRechargeOrder.setOrderStatus(orderStatus);
        mbrRechargeOrder.setModifyDate(new Date());
        mbrRechargeOrderMapper.update(mbrRechargeOrder);
        MbrRechargeOrderRecord mbrRechargeOrderRecord = new MbrRechargeOrderRecord();
        mbrRechargeOrderRecord.setMbrRechargeOrderId(mbrRechargeOrder.getId());
        mbrRechargeOrderRecord.setOrderStatus(orderStatus);
        mbrRechargeOrderRecordMapper.save(mbrRechargeOrderRecord);
    }


    /**
     * 处理会员充值订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */
    @Transactional
    @Override
    public void handleNotifyOfRechargeOrder(String outTradeNo, String transactionId, Date payDate) {
        // 根据订单号查询订单信息
        MbrRechargeOrder dbOrder = mbrRechargeOrderMapper.getByOrderNo(outTradeNo);
        if (dbOrder == null) {
            return;
        }

        // 如果订单已经处理则不在处理订单
        if (dbOrder.getOrderStatus().equals(MbrRechargeOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue())) {
            return;
        }

        // 更新充值订单
        MbrRechargeOrder updateOrder = new MbrRechargeOrder();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setOrderStatus(MbrRechargeOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        updateOrder.setTransactionNo(transactionId);
        updateOrder.setPayDate(payDate);
        updateOrder.setModifyDate(new Date());
        mbrRechargeOrderMapper.update(updateOrder);

        // 插入订单记录
        MbrRechargeOrderRecord saveOrderRecord = new MbrRechargeOrderRecord();
        saveOrderRecord.setId(IDUtil.getId());
        saveOrderRecord.setMbrRechargeOrderId(dbOrder.getId());

        saveOrderRecord.setOrderStatus(MbrRechargeOrderRecord.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        saveOrderRecord.setCreateDate(new Date());
        saveOrderRecord.setModifyDate(new Date());
        mbrRechargeOrderRecordMapper.save(saveOrderRecord);

        // 插入会员账户流水信息
        MbrBillRecord mbrBillRecord = new MbrBillRecord();
        mbrBillRecord.setId(IDUtil.getId());
        mbrBillRecord.setPkId(dbOrder.getId());
        mbrBillRecord.setWineryId(dbOrder.getWineryId());
        mbrBillRecord.setSignType(0);
        mbrBillRecord.setBillType(MbrBillRecord.BILL_TYPE_ENUM.RECHARGE_ACCT.getValue());
        mbrBillRecord.setBillRemark("账户充值");
        mbrBillRecord.setBillAmt(dbOrder.getPayRealAmt());
        mbrBillRecord.setCreateDate(new Date());
        mbrBillRecord.setModifyDate(new Date());
        mbrBillRecordMapper.save(mbrBillRecord);

        // 充值也送积分
        handleConsumeIntegral(dbOrder);


    }
    /**
     * 依据当前会员ID处理积分
     *
     * @param mbrRechargeOrder 会员订单
     */
    private void handleConsumeIntegral(MbrRechargeOrder mbrRechargeOrder) {
        // 如果会员没有父级，不用处理邀请返现
        String integralScale = SettingUtils.get().getConsumeAmtOfIntegral();
        Member curMbr = memberMapper.getById(mbrRechargeOrder.getMbrId());

        // 插入积分记录
        MbrIntegralRecord integralRecord = new MbrIntegralRecord();
        integralRecord.setId(IDUtil.getId());
        integralRecord.setWineryId(mbrRechargeOrder.getWineryId());
        integralRecord.setMbrId(mbrRechargeOrder.getMbrId());
        integralRecord.setActionType(MbrIntegralRecord.ACTION_TYPE_ENUM.RECHARGE_ACCT.getValue());
        integralRecord.setPkId(mbrRechargeOrder.getId());
        integralRecord.setSignType(1);

        // 计算积分
        BigDecimal payRealAmt = mbrRechargeOrder.getPayRealAmt();
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
