package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.model.app.MbrRechargeOrder;
import com.changfa.frame.service.mybatis.common.BaseService;

import java.math.BigDecimal;
import java.util.Map;

public interface MbrRechargeOrderService extends BaseService<MbrRechargeOrder, Long> {

    /**
     * 会员新建订单
     * @param mbrId
     * @param wineryId
     * @param payAmt

     */
    Map<String, Object> unifiedOrder(Long mbrId, Long wineryId, BigDecimal payAmt);


    /**
     * 修改订单状态
     * @param id
     * @param orderStatus
     */
    void update(Long id, Integer orderStatus);
}
