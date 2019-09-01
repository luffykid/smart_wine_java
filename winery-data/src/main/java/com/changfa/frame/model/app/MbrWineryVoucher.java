/*
 * MbrWineryVoucher.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-01 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒庄优惠券
 *
 * @version 1.0 2019-09-01
 */
public class MbrWineryVoucher extends BaseEntity {

    private static final long serialVersionUID = 447945526369845248L;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 酒庄优惠券实例
     */
    private Long wineryVoucherInstId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 券类型：
     * 1：代金券
     * 2：折扣券
     * 3：礼品券
     */
    private Integer type;

    /**
     * 优惠券金额
     */
    private BigDecimal voucherAmt;

    /**
     * 优惠券折扣比例
     */
    private BigDecimal voucherDiscount;

    /**
     * 商品SKU id（礼品券）
     */
    private Long prodSkuId;

    /**
     * 启用类型
     * 1：满多少元可以使用
     * 2：每满多少元可以使用
     */
    private Integer enableType;

    /**
     * 启用金额
     */
    private BigDecimal enableAmt;

    /**
     * 消费限制数量
     */
    private Integer quantityLimitCnt;

    /**
     * 有效开始时间
     */
    private Date effectiveBeginDate;

    /**
     * 有效期结束时间
     */
    private Date effectiveEndDate;

    /**
     * 使用范围
     * 1：活动
     * 2：消费
     * 3：活动和消费
     */
    private Integer useScope;

    /**
     * 代金券数量
     */
    private Integer voucherCnt;


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
     * 获取酒庄优惠券实例
     */
    public Long getWineryVoucherInstId() {
        return wineryVoucherInstId;
    }

    /**
     * 设置酒庄优惠券实例
     */
    public void setWineryVoucherInstId(Long wineryVoucherInstId) {
        this.wineryVoucherInstId = wineryVoucherInstId;
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
     * 获取优惠券名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置优惠券名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取券类型：
     * 1：代金券
     * 2：折扣券
     * 3：礼品券
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置券类型：
     * 1：代金券
     * 2：折扣券
     * 3：礼品券
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取优惠券金额
     */
    public BigDecimal getVoucherAmt() {
        return voucherAmt;
    }

    /**
     * 设置优惠券金额
     */
    public void setVoucherAmt(BigDecimal voucherAmt) {
        this.voucherAmt = voucherAmt;
    }

    /**
     * 获取优惠券折扣比例
     */
    public BigDecimal getVoucherDiscount() {
        return voucherDiscount;
    }

    /**
     * 设置优惠券折扣比例
     */
    public void setVoucherDiscount(BigDecimal voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }

    /**
     * 获取商品SKU id（礼品券）
     */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品SKU id（礼品券）
     */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }

    /**
     * 获取启用类型
     * 1：满多少元可以使用
     * 2：每满多少元可以使用
     */
    public Integer getEnableType() {
        return enableType;
    }

    /**
     * 设置启用类型
     * 1：满多少元可以使用
     * 2：每满多少元可以使用
     */
    public void setEnableType(Integer enableType) {
        this.enableType = enableType;
    }

    /**
     * 获取启用金额
     */
    public BigDecimal getEnableAmt() {
        return enableAmt;
    }

    /**
     * 设置启用金额
     */
    public void setEnableAmt(BigDecimal enableAmt) {
        this.enableAmt = enableAmt;
    }

    /**
     * 获取消费限制数量
     */
    public Integer getQuantityLimitCnt() {
        return quantityLimitCnt;
    }

    /**
     * 设置消费限制数量
     */
    public void setQuantityLimitCnt(Integer quantityLimitCnt) {
        this.quantityLimitCnt = quantityLimitCnt;
    }

    /**
     * 获取有效开始时间
     */
    public Date getEffectiveBeginDate() {
        return effectiveBeginDate;
    }

    /**
     * 设置有效开始时间
     */
    public void setEffectiveBeginDate(Date effectiveBeginDate) {
        this.effectiveBeginDate = effectiveBeginDate;
    }

    /**
     * 获取有效期结束时间
     */
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    /**
     * 设置有效期结束时间
     */
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    /**
     * 获取使用范围
     * 1：活动
     * 2：消费
     * 3：活动和消费
     */
    public Integer getUseScope() {
        return useScope;
    }

    /**
     * 设置使用范围
     * 1：活动
     * 2：消费
     * 3：活动和消费
     */
    public void setUseScope(Integer useScope) {
        this.useScope = useScope;
    }

    /**
     * 获取代金券数量
     */
    public Integer getVoucherCnt() {
        return voucherCnt;
    }

    /**
     * 设置代金券数量
     */
    public void setVoucherCnt(Integer voucherCnt) {
        this.voucherCnt = voucherCnt;
    }
}