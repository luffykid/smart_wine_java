/*
 * Winery.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.model;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 
 * @version 1.0 2019-08-07
 */
public class Winery extends BaseEntity {

    private static final long serialVersionUID = 438946627177152512L;

    /** 酒庄名称 */
    private String name;

    /** 酒庄LOGO */
    private String logo;

    /** 酒庄loding图片 */
    private String loadingImg;

    /** 酒庄状态1：正常0：禁用 */
    private Integer status;

    /** 状态时间 */
    private Date statusTime;

    
    /**
     * 获取酒庄名称
    */
    public String getName() {
        return name;
    }
    
    /**
     * 设置酒庄名称
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * 获取酒庄LOGO
    */
    public String getLogo() {
        return logo;
    }
    
    /**
     * 设置酒庄LOGO
    */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }
    
    /**
     * 获取酒庄loding图片
    */
    public String getLoadingImg() {
        return loadingImg;
    }
    
    /**
     * 设置酒庄loding图片
    */
    public void setLoadingImg(String loadingImg) {
        this.loadingImg = loadingImg == null ? null : loadingImg.trim();
    }
    
    /**
     * 获取酒庄状态1：正常0：禁用
    */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置酒庄状态1：正常0：禁用
    */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取状态时间
    */
    public Date getStatusTime() {
        return statusTime;
    }
    
    /**
     * 设置状态时间
    */
    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }
}