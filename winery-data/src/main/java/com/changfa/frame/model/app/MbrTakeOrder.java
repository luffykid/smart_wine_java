/*
 * MbrTakeOrder.java
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
public class MbrTakeOrder extends BaseEntity {

    private static final long serialVersionUID = 445451806915428352L;

    /** 会员储酒订单ID */
    private Long mbrStoreOrderId;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 提取重量 */
    private BigDecimal takeWeight;

    /** 提取容量 */
    private BigDecimal takeCapacity;

    /** 配送方式1：自提2：邮寄 */
    private Integer deliveryMode;

    /** 包装价格 */
    private BigDecimal packagePrice;

    /** 总包装金额 */
    private BigDecimal totalPackageAmt;

    /** 支付总金额 */
    private BigDecimal payTotalAmt;

    /** 支付实际金额 */
    private BigDecimal payRealAmt;

    /** 订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败） */
    private BigDecimal orderStatus;

    /** 收货详细地址 */
    private Long shippingDetailAddr;

    /** 收货省份ID */
    private String shippingProvinceId;

    /** 收货市ID */
    private Long shippingCityId;

    /** 收货县地区 */
    private Long shippingCountyId;

    /** 交易单号【三方支付返回单号】 */
    private String transactionNo;

    /** 订单号【系统生成单号】 */
    private String orderNo;

    
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
     * 获取提取重量
    */
    public BigDecimal getTakeWeight() {
        return takeWeight;
    }
    
    /**
     * 设置提取重量
    */
    public void setTakeWeight(BigDecimal takeWeight) {
        this.takeWeight = takeWeight;
    }
    
    /**
     * 获取提取容量
    */
    public BigDecimal getTakeCapacity() {
        return takeCapacity;
    }
    
    /**
     * 设置提取容量
    */
    public void setTakeCapacity(BigDecimal takeCapacity) {
        this.takeCapacity = takeCapacity;
    }
    
    /**
     * 获取配送方式1：自提2：邮寄
    */
    public Integer getDeliveryMode() {
        return deliveryMode;
    }
    
    /**
     * 设置配送方式1：自提2：邮寄
    */
    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }
    
    /**
     * 获取包装价格
    */
    public BigDecimal getPackagePrice() {
        return packagePrice;
    }
    
    /**
     * 设置包装价格
    */
    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }
    
    /**
     * 获取总包装金额
    */
    public BigDecimal getTotalPackageAmt() {
        return totalPackageAmt;
    }
    
    /**
     * 设置总包装金额
    */
    public void setTotalPackageAmt(BigDecimal totalPackageAmt) {
        this.totalPackageAmt = totalPackageAmt;
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
    public BigDecimal getOrderStatus() {
        return orderStatus;
    }
    
    /**
     * 设置订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败）
    */
    public void setOrderStatus(BigDecimal orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    /**
     * 获取收货详细地址
    */
    public Long getShippingDetailAddr() {
        return shippingDetailAddr;
    }
    
    /**
     * 设置收货详细地址
    */
    public void setShippingDetailAddr(Long shippingDetailAddr) {
        this.shippingDetailAddr = shippingDetailAddr;
    }
    
    /**
     * 获取收货省份ID
    */
    public String getShippingProvinceId() {
        return shippingProvinceId;
    }
    
    /**
     * 设置收货省份ID
    */
    public void setShippingProvinceId(String shippingProvinceId) {
        this.shippingProvinceId = shippingProvinceId == null ? null : shippingProvinceId.trim();
    }
    
    /**
     * 获取收货市ID
    */
    public Long getShippingCityId() {
        return shippingCityId;
    }
    
    /**
     * 设置收货市ID
    */
    public void setShippingCityId(Long shippingCityId) {
        this.shippingCityId = shippingCityId;
    }
    
    /**
     * 获取收货县地区
    */
    public Long getShippingCountyId() {
        return shippingCountyId;
    }
    
    /**
     * 设置收货县地区
    */
    public void setShippingCountyId(Long shippingCountyId) {
        this.shippingCountyId = shippingCountyId;
    }
    
    /**
     * 获取交易单号【三方支付返回单号】
    */
    public String getTransactionNo() {
        return transactionNo;
    }
    
    /**
     * 设置交易单号【三方支付返回单号】
    */
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }
    
    /**
     * 获取订单号【系统生成单号】
    */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 设置订单号【系统生成单号】
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}