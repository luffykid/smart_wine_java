/*
 * WinerySightDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.model;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 
 * @version 1.0 2019-08-07
 */
public class WinerySightDetail extends BaseEntity {

    private static final long serialVersionUID = 438946627177152513L;

    /** 酒庄景点ID */
    private Long winerySightId;

    /** 商品skuID */
    private Long prodSkuId;

    /** 详情主题 */
    private String detailTitle;

    /** 详情简介 */
    private String detailBrief;

    /** 详情图片 */
    private String detailImg;

    /** 详情状态 */
    private Integer detailStatus;

    /** 详情排序 */
    private Integer sort;

    
    /**
     * 获取酒庄景点ID
    */
    public Long getWinerySightId() {
        return winerySightId;
    }
    
    /**
     * 设置酒庄景点ID
    */
    public void setWinerySightId(Long winerySightId) {
        this.winerySightId = winerySightId;
    }

    /**
     * 获取商品skuID
     */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品skuID
     */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }
    
    /**
     * 获取详情主题
    */
    public String getDetailTitle() {
        return detailTitle;
    }
    
    /**
     * 设置详情主题
    */
    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle == null ? null : detailTitle.trim();
    }
    
    /**
     * 获取详情简介
    */
    public String getDetailBrief() {
        return detailBrief;
    }
    
    /**
     * 设置详情简介
    */
    public void setDetailBrief(String detailBrief) {
        this.detailBrief = detailBrief == null ? null : detailBrief.trim();
    }
    
    /**
     * 获取详情图片
    */
    public String getDetailImg() {
        return detailImg;
    }
    
    /**
     * 设置详情图片
    */
    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg == null ? null : detailImg.trim();
    }
    
    /**
     * 获取详情状态
    */
    public Integer getDetailStatus() {
        return detailStatus;
    }
    
    /**
     * 设置详情状态
    */
    public void setDetailStatus(Integer detailStatus) {
        this.detailStatus = detailStatus;
    }
    
    /**
     * 获取详情排序
    */
    public Integer getSort() {
        return sort;
    }
    
    /**
     * 设置详情排序
    */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}