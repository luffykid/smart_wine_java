/*
 * MbrProdOrder.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员商品订单
 * @version 1.0 2019-08-25
 */
public class MbrProdOrder extends BaseEntity {

    private static final long serialVersionUID = 445452357556240384L;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 支付方式1：微信支付2：积分支付3：积分+微信支付 */
    private Integer payMode;

    /** 商品总数量 */
    private Integer prodTotalCnt;

    /** 实付总金额 */
    private BigDecimal payRealAmt;

    /** 支付积分 */
    private Long payIntegralCnt;

    /** 订单状态0：新建订单1：未支付（已生成预支付ID）2：已取消（取消订单）3：已支付（用户完成支付）4：支付成功（回调通知成功）5：支付失败（回调通知失败） */
    private Integer orderStatus;

    /** 收货详细地址 */
    private String shippingDetailAddr;

    /** 收货省份ID */
    private Long shippingProvinceId;

    /** 收货市ID */
    private Long shippingCityId;

    /** 收获地址县 */
    private Long shippingCountyId;

    /** 收货人名称 */
    private String shippingPersonName;

    /** 收货人电话 */
    private String shippingPersonPhone;

    /** 订单号【系统生成】 */
    private String orderNo;

    /** 交易单号【三方支付返回单号】 */
    private String transactionNo;

    @Transient
    private List<MbrProdOrderItem> mbrProdOrderItems;

    public List<MbrProdOrderItem> getMbrProdOrderItems() {
        return mbrProdOrderItems;
    }

    public void setMbrProdOrderItems(List<MbrProdOrderItem> mbrProdOrderItems) {
        this.mbrProdOrderItems = mbrProdOrderItems;
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
     * 获取收货省份ID
    */
    public Long getShippingProvinceId() {
        return shippingProvinceId;
    }
    
    /**
     * 设置收货省份ID
    */
    public void setShippingProvinceId(Long shippingProvinceId) {
        this.shippingProvinceId = shippingProvinceId;
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
     * 获取订单号【系统生成】
    */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 设置订单号【系统生成】
    */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
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
}