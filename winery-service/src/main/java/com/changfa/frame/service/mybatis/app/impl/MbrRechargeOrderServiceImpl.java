package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.util.OrderNoUtil;
import com.changfa.frame.mapper.app.MbrRechargeOrderMapper;
import com.changfa.frame.mapper.app.MbrRechargeOrderRecordMapper;
import com.changfa.frame.model.app.MbrRechargeOrder;
import com.changfa.frame.model.app.MbrRechargeOrderRecord;
import com.changfa.frame.service.mybatis.app.MbrRechargeOrderService;
import com.changfa.frame.service.mybatis.common.IDUtil;
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
}
