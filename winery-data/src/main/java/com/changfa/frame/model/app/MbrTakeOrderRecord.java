/*
 * MbrTakeOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;
import java.util.Date;

/**
 * 会员商品订单记录
 * @version 1.0 2019-08-23
 */
public class MbrTakeOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 444717074313379840L;

    /** 会员提酒订单ID */
    private Long mbrTakeOrderId;

    /** 订单状态 */
    private Integer orderStatus;

    /** 订单备注 */
    private String orderRemark;

    /** 状态审核人（后台管理员） */
    private Long auditAdminId;

    /** 状态审核时间 */
    private Date auditDate;

    
    /**
     * 获取会员提酒订单ID
    */
    public Long getMbrTakeOrderId() {
        return mbrTakeOrderId;
    }
    
    /**
     * 设置会员提酒订单ID
    */
    public void setMbrTakeOrderId(Long mbrTakeOrderId) {
        this.mbrTakeOrderId = mbrTakeOrderId;
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
    
    /**
     * 获取状态审核人（后台管理员）
    */
    public Long getAuditAdminId() {
        return auditAdminId;
    }
    
    /**
     * 设置状态审核人（后台管理员）
    */
    public void setAuditAdminId(Long auditAdminId) {
        this.auditAdminId = auditAdminId;
    }
    
    /**
     * 获取状态审核时间
    */
    public Date getAuditDate() {
        return auditDate;
    }
    
    /**
     * 设置状态审核时间
    */
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
}