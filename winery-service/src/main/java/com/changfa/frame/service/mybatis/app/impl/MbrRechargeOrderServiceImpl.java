package com.changfa.frame.service.mybatis.app.impl;

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
    public void unifiedOrder(Long mbrId, Long wineryId, BigDecimal payAmt) {
        MbrRechargeOrder mbrRechargeOrder = new MbrRechargeOrder();
        mbrRechargeOrder.setId(IDUtil.getId());
        mbrRechargeOrder.setMbrId(mbrId);
        mbrRechargeOrder.setWineryId(wineryId);
        mbrRechargeOrder.setPayTotalAmt(payAmt);
        mbrRechargeOrder.setPayRealAmt(payAmt);
        mbrRechargeOrder.setOrderStatus(1);
        mbrRechargeOrder.setCreateDate(new Date());
        mbrRechargeOrderMapper.save(mbrRechargeOrder);
        MbrRechargeOrderRecord mbrRechargeOrderRecord = new MbrRechargeOrderRecord();
        mbrRechargeOrderRecord.setId(IDUtil.getId());
        mbrRechargeOrderRecord.setMbrRechargeOrderId(mbrRechargeOrder.getId());
        mbrRechargeOrderRecord.setOrderStatus(1);
        mbrRechargeOrderRecord.setCreateDate(new Date());
        mbrRechargeOrderRecordMapper.save(mbrRechargeOrderRecord);
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
