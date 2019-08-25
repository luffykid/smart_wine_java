/*
 * MbrRechargeOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 
 * @version 1.0 2019-08-25
 */
public class MbrRechargeOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 445385281021935616L;

    /** 会员充值订单ID */
    private Long mbrRechargeOrderId;

    /** 订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败） */
    private Integer orderStatus;

    
    /**
     * 获取会员充值订单ID
    */
    public Long getMbrRechargeOrderId() {
        return mbrRechargeOrderId;
    }
    
    /**
     * 设置会员充值订单ID
    */
    public void setMbrRechargeOrderId(Long mbrRechargeOrderId) {
        this.mbrRechargeOrderId = mbrRechargeOrderId;
    }
    
    /**
     * 获取订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败）
    */
    public Integer getOrderStatus() {
        return orderStatus;
    }
    
    /**
     * 设置订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败）
    */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}