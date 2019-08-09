/*
 * WinerySight.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.model;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * @version 1.0 2019-08-07
 */
public class WinerySight extends BaseEntity {

    private static final long serialVersionUID = 438946627151986688L;

    /** 酒庄ID */
    private Long wirenyId;

    /** 景点名称 */
    private String sightName;

    /** 景点ICON */
    private String sightIcon;

    /** 景点详情 */
    private String sightDetail;

    /** 封面图 */
    private String coverImg;

    /** 景点经度 */
    private BigDecimal latitude;

    /** 地理维度 */
    private BigDecimal longitude;

    /** x坐标 */
    private BigDecimal xAxis;

    /** y坐标 */
    private BigDecimal yAxis;

    /** 点赞总数量 */
    private Long likeTotalCnt;

    /** 分享总次数 */
    private Integer shareTotalCnt;

    /** 是否为签到景点 */
    private Boolean isSign;

    
    /**
     * 获取酒庄ID
    */
    public Long getWirenyId() {
        return wirenyId;
    }
    
    /**
     * 设置酒庄ID
    */
    public void setWirenyId(Long wirenyId) {
        this.wirenyId = wirenyId;
    }
    
    /**
     * 获取景点名称
    */
    public String getSightName() {
        return sightName;
    }
    
    /**
     * 设置景点名称
    */
    public void setSightName(String sightName) {
        this.sightName = sightName == null ? null : sightName.trim();
    }
    
    /**
     * 获取景点ICON
    */
    public String getSightIcon() {
        return sightIcon;
    }
    
    /**
     * 设置景点ICON
    */
    public void setSightIcon(String sightIcon) {
        this.sightIcon = sightIcon == null ? null : sightIcon.trim();
    }
    
    /**
     * 获取景点详情
    */
    public String getSightDetail() {
        return sightDetail;
    }
    
    /**
     * 设置景点详情
    */
    public void setSightDetail(String sightDetail) {
        this.sightDetail = sightDetail == null ? null : sightDetail.trim();
    }
    
    /**
     * 获取封面图
    */
    public String getCoverImg() {
        return coverImg;
    }
    
    /**
     * 设置封面图
    */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg == null ? null : coverImg.trim();
    }
    
    /**
     * 获取景点经度
    */
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    /**
     * 设置景点经度
    */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    /**
     * 获取地理维度
    */
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    /**
     * 设置地理维度
    */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 获取x坐标
    */
    public BigDecimal getxAxis() {
        return xAxis;
    }
    
    /**
     * 设置x坐标
    */
    public void setxAxis(BigDecimal xAxis) {
        this.xAxis = xAxis;
    }
    
    /**
     * 获取y坐标
    */
    public BigDecimal getyAxis() {
        return yAxis;
    }
    
    /**
     * 设置y坐标
    */
    public void setyAxis(BigDecimal yAxis) {
        this.yAxis = yAxis;
    }
    
    /**
     * 获取点赞总数量
    */
    public Long getLikeTotalCnt() {
        return likeTotalCnt;
    }
    
    /**
     * 设置点赞总数量
    */
    public void setLikeTotalCnt(Long likeTotalCnt) {
        this.likeTotalCnt = likeTotalCnt;
    }
    
    /**
     * 获取分享总次数
    */
    public Integer getShareTotalCnt() {
        return shareTotalCnt;
    }
    
    /**
     * 设置分享总次数
    */
    public void setShareTotalCnt(Integer shareTotalCnt) {
        this.shareTotalCnt = shareTotalCnt;
    }
    
    /**
     * 获取是否为签到景点
    */
    public Boolean getIsSign() {
        return isSign;
    }
    
    /**
     * 设置是否为签到景点
    */
    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }
}