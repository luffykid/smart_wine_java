/*
 * MbrWineCustom.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员白酒定制
 * @version 1.0 2019-08-26
 */
public class MbrWineCustom extends BaseEntity {

    private static final long serialVersionUID = 445914963979010048L;

    /** 会员白酒定制订单 */
    private Long mbrWineCustomOrderId;

    /** 白酒定制ID */
    private Long wineCustomId;

    /** 白酒定制ID */
    private Long winCustomId;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 定制数量 */
    private Integer customCnt;

    /** 定制价格 */
    private BigDecimal customPrice;

    /** 定制总金额 */
    private BigDecimal customTotalAmt;

    /** 定制名称 */
    private String customName;

    /** 商品SKU 名称 */
    private Long skuName;

    
    /**
     * 获取会员白酒定制订单
    */
    public Long getMbrWineCustomOrderId() {
        return mbrWineCustomOrderId;
    }
    
    /**
     * 设置会员白酒定制订单
    */
    public void setMbrWineCustomOrderId(Long mbrWineCustomOrderId) {
        this.mbrWineCustomOrderId = mbrWineCustomOrderId;
    }
    
    /**
     * 获取白酒定制ID
    */
    public Long getWineCustomId() {
        return wineCustomId;
    }
    
    /**
     * 设置白酒定制ID
    */
    public void setWineCustomId(Long wineCustomId) {
        this.wineCustomId = wineCustomId;
    }
    
    /**
     * 获取白酒定制ID
    */
    public Long getWinCustomId() {
        return winCustomId;
    }
    
    /**
     * 设置白酒定制ID
    */
    public void setWinCustomId(Long winCustomId) {
        this.winCustomId = winCustomId;
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
     * 获取定制数量
    */
    public Integer getCustomCnt() {
        return customCnt;
    }
    
    /**
     * 设置定制数量
    */
    public void setCustomCnt(Integer customCnt) {
        this.customCnt = customCnt;
    }
    
    /**
     * 获取定制价格
    */
    public BigDecimal getCustomPrice() {
        return customPrice;
    }
    
    /**
     * 设置定制价格
    */
    public void setCustomPrice(BigDecimal customPrice) {
        this.customPrice = customPrice;
    }
    
    /**
     * 获取定制总金额
    */
    public BigDecimal getCustomTotalAmt() {
        return customTotalAmt;
    }
    
    /**
     * 设置定制总金额
    */
    public void setCustomTotalAmt(BigDecimal customTotalAmt) {
        this.customTotalAmt = customTotalAmt;
    }
    
    /**
     * 获取定制名称
    */
    public String getCustomName() {
        return customName;
    }
    
    /**
     * 设置定制名称
    */
    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
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
}