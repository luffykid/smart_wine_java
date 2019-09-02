/*
 * MbrCart.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-02 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 会员购物车
 * @version 1.0 2019-09-02
 */
public class MbrCart extends BaseEntity {

    private static final long serialVersionUID = 448446916116611072L;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 商品ID */
    private Long prodId;

    /** SKU id */
    private Long prodSkuId;

    /** 商品SKU数量 */
    private Integer prodSkuCnt;

    
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
     * 获取商品ID
    */
    public Long getProdId() {
        return prodId;
    }
    
    /**
     * 设置商品ID
    */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }
    
    /**
     * 获取SKU id
    */
    public Long getProdSkuId() {
        return prodSkuId;
    }
    
    /**
     * 设置SKU id
    */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }
    
    /**
     * 获取商品SKU数量
    */
    public Integer getProdSkuCnt() {
        return prodSkuCnt;
    }
    
    /**
     * 设置商品SKU数量
    */
    public void setProdSkuCnt(Integer prodSkuCnt) {
        this.prodSkuCnt = prodSkuCnt;
    }
}