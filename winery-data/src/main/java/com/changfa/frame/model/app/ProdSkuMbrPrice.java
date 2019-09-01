/*
 * ProdSkuMbrPrice.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * sku会员价格
 * @version 1.0 2019-08-22
 */
public class ProdSkuMbrPrice extends BaseEntity {

    private static final long serialVersionUID = 444292596760576000L;

    /** sku ID */
    private Long prodSkuId;

    /** 会员等级ID */
    private Long mbrLevelId;

    /** 会员等级价格 */
    private BigDecimal mbrLevelPrice;

    
    /**
     * 获取sku ID
    */
    public Long getProdSkuId() {
        return prodSkuId;
    }
    
    /**
     * 设置sku ID
    */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }
    
    /**
     * 获取会员等级ID
    */
    public Long getMbrLevelId() {
        return mbrLevelId;
    }
    
    /**
     * 设置会员等级ID
    */
    public void setMbrLevelId(Long mbrLevelId) {
        this.mbrLevelId = mbrLevelId;
    }
    
    /**
     * 获取会员等级价格
    */
    public BigDecimal getMbrLevelPrice() {
        return mbrLevelPrice;
    }
    
    /**
     * 设置会员等级价格
    */
    public void setMbrLevelPrice(BigDecimal mbrLevelPrice) {
        this.mbrLevelPrice = mbrLevelPrice;
    }

    @Override
    public String toString() {
        return "ProdSkuMbrPrice{" +
                "prodSkuId=" + prodSkuId +
                ", mbrLevelId=" + mbrLevelId +
                ", mbrLevelPrice=" + mbrLevelPrice +
                '}';
    }
}