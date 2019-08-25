/*
 * MbrProdOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 会员商品订单记录
 * @version 1.0 2019-08-25
 */
public class MbrProdOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 445452963490562048L;

    /** 会员商品订单ID */
    private Long mbrProdOrderId;

    /** 订单状态 */
    private Integer orderStatus;

    /** 订单备注 */
    private String orderRemark;

    
    /**
     * 获取会员商品订单ID
    */
    public Long getMbrProdOrderId() {
        return mbrProdOrderId;
    }
    
    /**
     * 设置会员商品订单ID
    */
    public void setMbrProdOrderId(Long mbrProdOrderId) {
        this.mbrProdOrderId = mbrProdOrderId;
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