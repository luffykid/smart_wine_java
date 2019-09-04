package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.*;
import com.changfa.frame.model.app.*;
import com.changfa.frame.service.mybatis.app.MbrAdjustOrderService;
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

/**
 * 会员自调酒业务实现
 *
 * @author wyy
 * @date 2019-08-31 01:19
 */
@Service("mbrAdjustOrderServiceImpl")
public class MbrAdjustOrderServiceImpl extends BaseServiceImpl<MbrAdjustOrder, Long> implements MbrAdjustOrderService {

    @Autowired
    private MbrAdjustOrderMapper mbrAdjustOrderMapper;

    @Autowired
    private MbrAdjustOrderRecordMapper mbrAdjustOrderRecordMapper;

    @Autowired
    private WineryAdjustWineMapper wineryAdjustWineMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MbrBillRecordMapper mbrBillRecordMapper;

    @Autowired
    private MbrIntegralRecordMapper mbrIntegralRecordMapper;
    /**
     * 生成自调酒订单
     *
     * @return  订单号 orderNo
     */
    @Transactional
    @Override
    public Map<String, Object> buildAdjustOrder(Long adjustId, Member member,BigDecimal payTotalAmt){

        //1.获取相关信息
        WineryAdjustWine wineryAdjustWine = wineryAdjustWineMapper.getById(adjustId);
        String orderNo = OrderNoUtil.get();
        //2.保存MbrAdjustOrder
        MbrAdjustOrder mbrAdjustOrder = new MbrAdjustOrder();
        //四个id
        mbrAdjustOrder.setId(IDUtil.getId());
        mbrAdjustOrder.setWineryAdjustWineId(adjustId);
        mbrAdjustOrder.setMbrId(member.getId());
        mbrAdjustOrder.setWineryId(wineryAdjustWine.getWineryId());
        mbrAdjustOrder.setPayMode(MbrAdjustOrder.PAY_MODE_ENUM.WX_MINI_MODE.getValue());//微信支付
        mbrAdjustOrder.setPayTotalAmt(payTotalAmt);
        mbrAdjustOrder.setPayRealAmt(payTotalAmt);
        //mbrAdjustOrder.setPrintImg();
        mbrAdjustOrder.setOrderStatus(MbrAdjustOrder.ORDER_STATUS_ENUM.PAY_NOT.getValue());//未支付
        mbrAdjustOrder.setOrderNo(orderNo);
        //mbrAdjustOrder.setPayDate();//支付时间
        mbrAdjustOrder.setCreateDate(new Date());
        mbrAdjustOrderMapper.save(mbrAdjustOrder);

        //3.保存MbrAdjustOrderRecord
        MbrAdjustOrderRecord mbrAdjustOrderRecord = new MbrAdjustOrderRecord();
        mbrAdjustOrderRecord.setId(IDUtil.getId());
        mbrAdjustOrderRecord.setMbrAdjustOrderId(mbrAdjustOrder.getId());
        mbrAdjustOrderRecord.setWineryId(wineryAdjustWine.getWineryId());
        mbrAdjustOrderRecord.setPayTotalAmt(payTotalAmt);
        mbrAdjustOrderRecord.setPayRealAmt(payTotalAmt);
        mbrAdjustOrderRecord.setOrderStatus(MbrAdjustOrderRecord.ORDER_STATUS_ENUM.PAY_NOT.getValue());
        mbrAdjustOrderRecord.setCreateDate(new Date());


        // 4.返回订单号和金额 用于微信预下单
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("orderNo",orderNo);
        returnMap.put("payTotalAmt",mbrAdjustOrder.getPayTotalAmt());

        return returnMap;
    }


    /**
     * 处理会员自调酒订单支付
     *
     * @param outTradeNo    商品订单号
     * @param transactionId 微信支付返回的订单号
     * @param payDate       成功支付时间
     */

    @Transactional
    @Override
    public void handleNotifyOfAdjustOrder(String outTradeNo, String transactionId, Date payDate) {
        // 根据订单号查询订单信息
        MbrAdjustOrder dbOrder = mbrAdjustOrderMapper.getByOrderNo(outTradeNo);
        if (dbOrder == null) {
            return;
        }

        // 如果订单已经处理则不在处理订单
        if (dbOrder.getOrderStatus().equals(MbrAdjustOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue())) {
            return;
        }

        // 更新自调酒订单
        MbrAdjustOrder updateOrder = new MbrAdjustOrder();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setOrderStatus(MbrAdjustOrder.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        updateOrder.setTransactionNo(transactionId);
        updateOrder.setPayDate(payDate);
        updateOrder.setModifyDate(new Date());
        mbrAdjustOrderMapper.update(updateOrder);

        // 插入订单记录
        MbrAdjustOrderRecord saveOrderRecord = new MbrAdjustOrderRecord();
        saveOrderRecord.setId(IDUtil.getId());
        saveOrderRecord.setMbrAdjustOrderId(dbOrder.getId());
        saveOrderRecord.setOrderStatus(MbrAdjustOrderRecord.ORDER_STATUS_ENUM.PAY_SUCCESS.getValue());
        saveOrderRecord.setCreateDate(new Date());
        saveOrderRecord.setModifyDate(new Date());
        mbrAdjustOrderRecordMapper.save(saveOrderRecord);

        // 插入会员账户流水信息
        MbrBillRecord mbrBillRecord = new MbrBillRecord();
        mbrBillRecord.setId(IDUtil.getId());
        mbrBillRecord.setPkId(dbOrder.getId());
        mbrBillRecord.setWineryId(dbOrder.getWineryId());
        mbrBillRecord.setSignType(0);
        mbrBillRecord.setBillType(MbrBillRecord.BILL_TYPE_ENUM.ADJUST_CUSTOM.getValue());
        mbrBillRecord.setBillRemark("自调酒消费");
        mbrBillRecord.setBillAmt(dbOrder.getPayRealAmt());
        mbrBillRecord.setCreateDate(new Date());
        mbrBillRecord.setModifyDate(new Date());
        mbrBillRecordMapper.save(mbrBillRecord);



        // 消费送积分
        handleConsumeIntegral(dbOrder);



    }

    /**
     * 依据当前会员ID处理积分
     *
     * @param mbrAdjustOrder 会员订单
     */
    private void handleConsumeIntegral(MbrAdjustOrder mbrAdjustOrder) {
        // 如果会员没有父级，不用处理邀请返现
        String integralScale = SettingUtils.get().getConsumeAmtOfIntegral();
        Member curMbr = memberMapper.getById(mbrAdjustOrder.getMbrId());

        // 插入积分记录
        MbrIntegralRecord integralRecord = new MbrIntegralRecord();
        integralRecord.setId(IDUtil.getId());
        integralRecord.setWineryId(mbrAdjustOrder.getWineryId());
        integralRecord.setMbrId(mbrAdjustOrder.getMbrId());
        integralRecord.setActionType(MbrIntegralRecord.ACTION_TYPE_ENUM.ADJUST_CUSTOM.getValue());
        integralRecord.setPkId(mbrAdjustOrder.getId());
        integralRecord.setSignType(1);

        // 计算积分
        BigDecimal payRealAmt = mbrAdjustOrder.getPayRealAmt();
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
