/*
 * MbrRechargeOrder.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * @version 1.0 2019-08-25
 */
public class MbrRechargeOrder extends BaseEntity {

    private static final long serialVersionUID = 445385130505142272L;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 支付总金额 */
    private BigDecimal payTotalAmt;

    /** 支付实际金额 */
    private BigDecimal payRealAmt;

    /** 订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败） */
    private Integer orderStatus;

    
    /**
     * 获取酒庄ID
    */
    public Long getWineryId() {
        return wineryId;
    }
    
    /**
     * 设置酒庄ID
    */
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }
    
    /**
     * 获取会员ID
    */
    public Long getMbrId() {
        return mbrId;
    }
    
    /**
     * 设置会员ID
    */
    public void setMbrId(Long mbrId) {
        this.mbrId = mbrId;
    }
    
    /**
     * 获取支付总金额
    */
    public BigDecimal getPayTotalAmt() {
        return payTotalAmt;
    }
    
    /**
     * 设置支付总金额
    */
    public void setPayTotalAmt(BigDecimal payTotalAmt) {
        this.payTotalAmt = payTotalAmt;
    }
    
    /**
     * 获取支付实际金额
    */
    public BigDecimal getPayRealAmt() {
        return payRealAmt;
    }
    
    /**
     * 设置支付实际金额
    */
    public void setPayRealAmt(BigDecimal payRealAmt) {
        this.payRealAmt = payRealAmt;
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