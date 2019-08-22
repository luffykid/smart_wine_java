/*
 * ProdImg.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 商品图片表
 * @version 1.0 2019-08-22
 */
public class ProdImg extends BaseEntity {

    private static final long serialVersionUID = 444293149590814720L;

    /** 商品ID */
    private Long prodId;

    /** 商品名称 */
    private String imgName;

    /** 商品图片地址 */
    private String imgAddrr;

    /** 图片备注 */
    private String imgRemark;

    /** 图片排序 */
    private Integer sort;

    
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
     * 获取商品名称
    */
    public String getImgName() {
        return imgName;
    }
    
    /**
     * 设置商品名称
    */
    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }
    
    /**
     * 获取商品图片地址
    */
    public String getImgAddrr() {
        return imgAddrr;
    }
    
    /**
     * 设置商品图片地址
    */
    public void setImgAddrr(String imgAddrr) {
        this.imgAddrr = imgAddrr == null ? null : imgAddrr.trim();
    }
    
    /**
     * 获取图片备注
    */
    public String getImgRemark() {
        return imgRemark;
    }
    
    /**
     * 设置图片备注
    */
    public void setImgRemark(String imgRemark) {
        this.imgRemark = imgRemark == null ? null : imgRemark.trim();
    }
    
    /**
     * 获取图片排序
    */
    public Integer getSort() {
        return sort;
    }
    
    /**
     * 设置图片排序
    */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}