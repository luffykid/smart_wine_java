/*
 * MbrWineCustomOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 
 * @version 1.0 2019-08-26
 */
public class MbrWineCustomOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 445916972861882368L;

    /** 订单ID */
    private Long mbrWineCustomOrderId;

    /** 订单状态 */
    private Long orderStatus;

    /** 状态变更时间 */
    private Date statusDate;

    /** 订单状态备注 */
    private String orderRecordRemark;

    
    /**
     * 获取订单ID
    */
    public Long getMbrWineCustomOrderId() {
        return mbrWineCustomOrderId;
    }
    
    /**
     * 设置订单ID
    */
    public void setMbrWineCustomOrderId(Long mbrWineCustomOrderId) {
        this.mbrWineCustomOrderId = mbrWineCustomOrderId;
    }
    
    /**
     * 获取订单状态
    */
    public Long getOrderStatus() {
        return orderStatus;
    }
    
    /**
     * 设置订单状态
    */
    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    /**
     * 获取状态变更时间
    */
    public Date getStatusDate() {
        return statusDate;
    }
    
    /**
     * 设置状态变更时间
    */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
    
    /**
     * 获取订单状态备注
    */
    public String getOrderRecordRemark() {
        return orderRecordRemark;
    }
    
    /**
     * 设置订单状态备注
    */
    public void setOrderRecordRemark(String orderRecordRemark) {
        this.orderRecordRemark = orderRecordRemark == null ? null : orderRecordRemark.trim();
    }
}