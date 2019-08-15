/*
 * Winery.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒庄
 *
 * @version 1.0 2019-08-14
 */
public class Winery extends BaseEntity {

    private static final long serialVersionUID = 441544332273516544L;

    /**
     * 酒庄名称
     */
    private String name;

    /**
     * 酒庄LOGO
     */
    private String logo;

    /**
     * 酒庄loading图片
     */
    private String loadingImg;

    /**
     * 酒庄状态
     * 1：正常
     * 0：禁用
     */
    private Integer status;

    /**
     * 状态时间
     */
    private Date statusTime;

    /**
     * a点经度
     */
    private BigDecimal aLatitude;

    /**
     * a点纬度
     */
    private BigDecimal aLongitude;

    /**
     * b点经度
     */
    private BigDecimal bLatitude;

    /**
     * b点纬度
     */
    private BigDecimal bLongitude;

    /**
     * c点经度
     */
    private BigDecimal cLatitude;

    /**
     * c点纬度
     */
    private BigDecimal cLongitude;

    /**
     * d点经度
     */
    private Long dLatitude;

    /**
     * d点纬度
     */
    private BigDecimal dLongitude;


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
     * 获取酒庄loading图片
     */
    public String getLoadingImg() {
        return loadingImg;
    }

    /**
     * 设置酒庄loading图片
     */
    public void setLoadingImg(String loadingImg) {
        this.loadingImg = loadingImg == null ? null : loadingImg.trim();
    }

    /**
     * 获取酒庄状态
     * 1：正常
     * 0：禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置酒庄状态
     * 1：正常
     * 0：禁用
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

    /**
     * 获取a点经度
     */
    public BigDecimal getaLatitude() {
        return aLatitude;
    }

    /**
     * 设置a点经度
     */
    public void setaLatitude(BigDecimal aLatitude) {
        this.aLatitude = aLatitude;
    }

    /**
     * 获取a点纬度
     */
    public BigDecimal getaLongitude() {
        return aLongitude;
    }

    /**
     * 设置a点纬度
     */
    public void setaLongitude(BigDecimal aLongitude) {
        this.aLongitude = aLongitude;
    }

    /**
     * 获取b点经度
     */
    public BigDecimal getbLatitude() {
        return bLatitude;
    }

    /**
     * 设置b点经度
     */
    public void setbLatitude(BigDecimal bLatitude) {
        this.bLatitude = bLatitude;
    }

    /**
     * 获取b点纬度
     */
    public BigDecimal getbLongitude() {
        return bLongitude;
    }

    /**
     * 设置b点纬度
     */
    public void setbLongitude(BigDecimal bLongitude) {
        this.bLongitude = bLongitude;
    }

    /**
     * 获取c点经度
     */
    public BigDecimal getcLatitude() {
        return cLatitude;
    }

    /**
     * 设置c点经度
     */
    public void setcLatitude(BigDecimal cLatitude) {
        this.cLatitude = cLatitude;
    }

    /**
     * 获取c点纬度
     */
    public BigDecimal getcLongitude() {
        return cLongitude;
    }

    /**
     * 设置c点纬度
     */
    public void setcLongitude(BigDecimal cLongitude) {
        this.cLongitude = cLongitude;
    }

    /**
     * 获取d点经度
     */
    public Long getdLatitude() {
        return dLatitude;
    }

    /**
     * 设置d点经度
     */
    public void setdLatitude(Long dLatitude) {
        this.dLatitude = dLatitude;
    }

    /**
     * 获取d点纬度
     */
    public BigDecimal getdLongitude() {
        return dLongitude;
    }

    /**
     * 设置d点纬度
     */
    public void setdLongitude(BigDecimal dLongitude) {
        this.dLongitude = dLongitude;
    }
}