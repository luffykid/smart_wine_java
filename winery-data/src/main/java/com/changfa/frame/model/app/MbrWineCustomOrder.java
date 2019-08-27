/*
 * MbrWineCustomOrder.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员白酒定制订单
 * @version 1.0 2019-08-26
 */
public class MbrWineCustomOrder extends BaseEntity {

    private static final long serialVersionUID = 445920555988680704L;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 定制总数量 */
    private Integer customTotalCnt;

    /** 支付总金额 */
    private BigDecimal payTotalAmt;

    /** 实付总金额 */
    private BigDecimal payRealAmt;

    /** 订单状态
0：新建订单
1：未支付（已生成预支付ID）
2：已取消（取消订单）
3：已支付（用户完成支付）
4：支付成功（回调通知成功）
5：支付失败（回调通知失败） */
    private Integer orderStatus;

    /** 收货详细地址 */
    private String shippingDetailAddr;

    /** 收获省份ID */
    private Long shippingProvinceId;

    /** 收获市ID */
    private Long shippingCityId;

    /** 收获地址县 */
    private Long shippingCountyId;

    /** 收货人名称 */
    private String shippingPersonName;

    /** 收货人电话 */
    private String shippingPersonPhone;

    /** 交易单号【三方支付返回单号】 */
    private String transactionNo;

    /** 订单号【系统生成单号】 */
    private String orderNo;

    
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
     * 获取定制总数量
    */
    public Integer getCustomTotalCnt() {
        return customTotalCnt;
    }
    
    /**
     * 设置定制总数量
    */
    public void setCustomTotalCnt(Integer customTotalCnt) {
        this.customTotalCnt = customTotalCnt;
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
     * 获取订单状态
0：新建订单
1：未支付（已生成预支付ID）
2：已取消（取消订单）
3：已支付（用户完成支付）
4：支付成功（回调通知成功）
5：支付失败（回调通知失败）
    */
    public Integer getOrderStatus() {
        return orderStatus;
    }
    
    /**
     * 设置订单状态
0：新建订单
1：未支付（已生成预支付ID）
2：已取消（取消订单）
3：已支付（用户完成支付）
4：支付成功（回调通知成功）
5：支付失败（回调通知失败）
    */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    /**
     * 获取收货详细地址
    */
    public String getShippingDetailAddr() {
        return shippingDetailAddr;
    }
    
    /**
     * 设置收货详细地址
    */
    public void setShippingDetailAddr(String shippingDetailAddr) {
        this.shippingDetailAddr = shippingDetailAddr == null ? null : shippingDetailAddr.trim();
    }
    
    /**
     * 获取收获省份ID
    */
    public Long getShippingProvinceId() {
        return shippingProvinceId;
    }
    
    /**
     * 设置收获省份ID
    */
    public void setShippingProvinceId(Long shippingProvinceId) {
        this.shippingProvinceId = shippingProvinceId;
    }
    
    /**
     * 获取收获市ID
    */
    public Long getShippingCityId() {
        return shippingCityId;
    }
    
    /**
     * 设置收获市ID
    */
    public void setShippingCityId(Long shippingCityId) {
        this.shippingCityId = shippingCityId;
    }
    
    /**
     * 获取收获地址县
    */
    public Long getShippingCountyId() {
        return shippingCountyId;
    }
    
    /**
     * 设置收获地址县
    */
    public void setShippingCountyId(Long shippingCountyId) {
        this.shippingCountyId = shippingCountyId;
    }
    
    /**
     * 获取收货人名称
    */
    public String getShippingPersonName() {
        return shippingPersonName;
    }
    
    /**
     * 设置收货人名称
    */
    public void setShippingPersonName(String shippingPersonName) {
        this.shippingPersonName = shippingPersonName == null ? null : shippingPersonName.trim();
    }
    
    /**
     * 获取收货人电话
    */
    public String getShippingPersonPhone() {
        return shippingPersonPhone;
    }
    
    /**
     * 设置收货人电话
    */
    public void setShippingPersonPhone(String shippingPersonPhone) {
        this.shippingPersonPhone = shippingPersonPhone == null ? null : shippingPersonPhone.trim();
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