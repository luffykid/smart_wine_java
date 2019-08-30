package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrRechargeOrder;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.math.BigDecimal;

public interface MbrRechargeOrderService extends BaseService<MbrRechargeOrder, Long> {

    /**
     * 会员新建订单
     * @param mbrId
     * @param wineryId
     * @param payTotalAmt
     * @param payRealAmt
     */
    void recharge(Long mbrId, Long wineryId, BigDecimal payTotalAmt, BigDecimal payRealAmt);


    /**
     * 修改订单状态
     * @param id
     * @param orderStatus
     */
    void update(Long id, Integer orderStatus);
}
