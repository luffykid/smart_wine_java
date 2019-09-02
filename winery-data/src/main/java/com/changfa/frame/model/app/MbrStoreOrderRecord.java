/*
 * MbrStoreOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-02 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 会员商品订单记录
 * @version 1.0 2019-09-02
 */
public class MbrStoreOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 448120558047985664L;

    /** 会员储酒订单ID */
    private Long mbrStoreOrderId;

    /** 订单状态 */
    private Integer orderStatus;

    /** 订单备注 */
    private String orderRemark;

    
    /**
     * 获取会员储酒订单ID
    */
    public Long getMbrStoreOrderId() {
        return mbrStoreOrderId;
    }
    
    /**
     * 设置会员储酒订单ID
    */
    public void setMbrStoreOrderId(Long mbrStoreOrderId) {
        this.mbrStoreOrderId = mbrStoreOrderId;
    }
    
    /**
     * 获取订单状态
    */
    public Integer getOrderStatus() {
        return orderStatus;
    }
    
    /**
     * 设置订单状态
    */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    /**
     * 获取订单备注
    */
    public String getOrderRemark() {
        return orderRemark;
    }
    
    /**
     * 设置订单备注
    */
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }
}