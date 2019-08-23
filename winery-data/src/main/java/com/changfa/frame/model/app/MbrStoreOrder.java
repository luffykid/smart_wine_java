/*
 * MbrStoreOrder.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员商品订单
 * @version 1.0 2019-08-23
 */
public class MbrStoreOrder extends BaseEntity {

    private static final long serialVersionUID = 444707106398928896L;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 云酒窖活动ID */
    private Long wineCellarActivityId;

    /** 支付方式1：微信支付2：积分支付3：积分+微信支付 */
    private Integer payMode;

    /** 商品总数量 */
    private Integer prodTotalCnt;

    /** 支付总金额 */
    private BigDecimal payTotalAmt;

    /** 实付总金额 */
    private BigDecimal payRealAmt;

    /** 支付积分 */
    private Long payIntegralCnt;

    /** 订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败） */
    private Integer orderStatus;

    /** 总原始重量 */
    private BigDecimal totalOrgWeight;

    /** 总原始容量 */
    private BigDecimal totalOrgCapacity;

    /** 总增长重量 */
    private BigDecimal totalIncreaseWeight;

    /** 总增长容量 */
    private BigDecimal totalIncreaseCapacity;

    /** 总剩余量 */
    private BigDecimal totalRemainWeight;

    /** 总剩余容量 */
    private BigDecimal totalRemainCapacity;

    
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
     * 获取云酒窖活动ID
    */
    public Long getWineCellarActivityId() {
        return wineCellarActivityId;
    }
    
    /**
     * 设置云酒窖活动ID
    */
    public void setWineCellarActivityId(Long wineCellarActivityId) {
        this.wineCellarActivityId = wineCellarActivityId;
    }
    
    /**
     * 获取支付方式1：微信支付2：积分支付3：积分+微信支付
    */
    public Integer getPayMode() {
        return payMode;
    }
    
    /**
     * 设置支付方式1：微信支付2：积分支付3：积分+微信支付
    */
    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }
    
    /**
     * 获取商品总数量
    */
    public Integer getProdTotalCnt() {
        return prodTotalCnt;
    }
    
    /**
     * 设置商品总数量
    */
    public void setProdTotalCnt(Integer prodTotalCnt) {
        this.prodTotalCnt = prodTotalCnt;
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
     * 获取实付总金额
    */
    public BigDecimal getPayRealAmt() {
        return payRealAmt;
    }
    
    /**
     * 设置实付总金额
    */
    public void setPayRealAmt(BigDecimal payRealAmt) {
        this.payRealAmt = payRealAmt;
    }
    
    /**
     * 获取支付积分
    */
    public Long getPayIntegralCnt() {
        return payIntegralCnt;
    }
    
    /**
     * 设置支付积分
    */
    public void setPayIntegralCnt(Long payIntegralCnt) {
        this.payIntegralCnt = payIntegralCnt;
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
    
    /**
     * 获取总原始重量
    */
    public BigDecimal getTotalOrgWeight() {
        return totalOrgWeight;
    }
    
    /**
     * 设置总原始重量
    */
    public void setTotalOrgWeight(BigDecimal totalOrgWeight) {
        this.totalOrgWeight = totalOrgWeight;
    }
    
    /**
     * 获取总原始容量
    */
    public BigDecimal getTotalOrgCapacity() {
        return totalOrgCapacity;
    }
    
    /**
     * 设置总原始容量
    */
    public void setTotalOrgCapacity(BigDecimal totalOrgCapacity) {
        this.totalOrgCapacity = totalOrgCapacity;
    }
    
    /**
     * 获取总增长重量
    */
    public BigDecimal getTotalIncreaseWeight() {
        return totalIncreaseWeight;
    }
    
    /**
     * 设置总增长重量
    */
    public void setTotalIncreaseWeight(BigDecimal totalIncreaseWeight) {
        this.totalIncreaseWeight = totalIncreaseWeight;
    }
    
    /**
     * 获取总增长容量
    */
    public BigDecimal getTotalIncreaseCapacity() {
        return totalIncreaseCapacity;
    }
    
    /**
     * 设置总增长容量
    */
    public void setTotalIncreaseCapacity(BigDecimal totalIncreaseCapacity) {
        this.totalIncreaseCapacity = totalIncreaseCapacity;
    }
    
    /**
     * 获取总剩余量
    */
    public BigDecimal getTotalRemainWeight() {
        return totalRemainWeight;
    }
    
    /**
     * 设置总剩余量
    */
    public void setTotalRemainWeight(BigDecimal totalRemainWeight) {
        this.totalRemainWeight = totalRemainWeight;
    }
    
    /**
     * 获取总剩余容量
    */
    public BigDecimal getTotalRemainCapacity() {
        return totalRemainCapacity;
    }
    
    /**
     * 设置总剩余容量
    */
    public void setTotalRemainCapacity(BigDecimal totalRemainCapacity) {
        this.totalRemainCapacity = totalRemainCapacity;
    }
}