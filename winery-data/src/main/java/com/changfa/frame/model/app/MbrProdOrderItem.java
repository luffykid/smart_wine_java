/*
 * MbrProdOrderItem.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员白酒定制
 *
 * @version 1.0 2019-08-25
 */
public class MbrProdOrderItem extends BaseEntity {

    private static final long serialVersionUID = 445452715858853888L;

    /**
     * 商品订单ID
     */
    private Long mbrProdOrderId;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 商品sku ID
     */
    private Long prodSkuId;

    /**
     * 商品sku数量
     */
    private Integer prodSkuCnt;

    /**
     * 商品SKU 名称
     */
    private Long skuName;

    /**
     * sku市场价
     */
    private BigDecimal skuMarketPrice;

    /**
     * sku售卖价
     */
    private BigDecimal skuSellPrice;

    private Long skuMbrPrice;

    /**
     * 是否支持积分支付0：不支持积分支付1：支持积分支付
     */
    private Boolean isIntegral;

    /**
     * 积分金额
     */
    private BigDecimal integralAmt;

    /**
     * 积分数量
     */
    private Long integralCnt;


    /**
     * 获取商品订单ID
     */
    public Long getMbrProdOrderId() {
        return mbrProdOrderId;
    }

    /**
     * 设置商品订单ID
     */
    public void setMbrProdOrderId(Long mbrProdOrderId) {
        this.mbrProdOrderId = mbrProdOrderId;
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
     * 获取商品sku ID
     */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品sku ID
     */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }

    /**
     * 获取商品sku数量
     */
    public Integer getProdSkuCnt() {
        return prodSkuCnt;
    }

    /**
     * 设置商品sku数量
     */
    public void setProdSkuCnt(Integer prodSkuCnt) {
        this.prodSkuCnt = prodSkuCnt;
    }

    /**
     * 获取商品SKU 名称
     */
    public Long getSkuName() {
        return skuName;
    }

    /**
     * 设置商品SKU 名称
     */
    public void setSkuName(Long skuName) {
        this.skuName = skuName;
    }

    /**
     * 获取sku市场价
     */
    public BigDecimal getSkuMarketPrice() {
        return skuMarketPrice;
    }

    /**
     * 设置sku市场价
     */
    public void setSkuMarketPrice(BigDecimal skuMarketPrice) {
        this.skuMarketPrice = skuMarketPrice;
    }

    /**
     * 获取sku售卖价
     */
    public BigDecimal getSkuSellPrice() {
        return skuSellPrice;
    }

    /**
     * 设置sku售卖价
     */
    public void setSkuSellPrice(BigDecimal skuSellPrice) {
        this.skuSellPrice = skuSellPrice;
    }

    public Long getSkuMbrPrice() {
        return skuMbrPrice;
    }

    public void setSkuMbrPrice(Long skuMbrPrice) {
        this.skuMbrPrice = skuMbrPrice;
    }

    /**
     * 获取是否支持积分支付0：不支持积分支付1：支持积分支付
     */
    public Boolean getIsIntegral() {
        return isIntegral;
    }

    /**
     * 设置是否支持积分支付0：不支持积分支付1：支持积分支付
     */
    public void setIsIntegral(Boolean isIntegral) {
        this.isIntegral = isIntegral;
    }

    /**
     * 获取积分金额
     */
    public BigDecimal getIntegralAmt() {
        return integralAmt;
    }

    /**
     * 设置积分金额
     */
    public void setIntegralAmt(BigDecimal integralAmt) {
        this.integralAmt = integralAmt;
    }

    /**
     * 获取积分数量
     */
    public Long getIntegralCnt() {
        return integralCnt;
    }

    /**
     * 设置积分数量
     */
    public void setIntegralCnt(Long integralCnt) {
        this.integralCnt = integralCnt;
    }
}