/*
 * WinerySight.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 酒庄景点表
 *
 * @version 1.0 2019-08-14
 */
public class WinerySight extends BaseEntity {

    private static final long serialVersionUID = 441544621940539392L;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 景点名称
     */
    private String sightName;

    /**
     * 景点ICO
     */
    private String sightIcon;

    /**
     * 景点详情
     */
    private String sightDetail;

    /**
     * 封面图
     */
    private String coverImg;

    /**
     * 景点经度
     */
    private BigDecimal latitude;

    /**
     * 地理纬度
     */
    private BigDecimal longitude;

    /**
     * 点赞总数量
     */
    private Long likeTotalCnt;

    /**
     * 分享总次数
     */
    private Integer shareTotalCnt;

    /**
     * 是否为签到景点
     * 0：否
     * 1：是
     */
    private Boolean isSign;

    /**
     * 景点图片
     */
    private  List<String> scenicImg;

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
     * 获取景点ICO
     */
    public String getSightIcon() {
        return sightIcon;
    }

    /**
     * 设置景点ICO
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
     * 获取地理纬度
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 设置地理纬度
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
     * 0：否
     * 1：是
     */
    public Boolean getIsSign() {
        return isSign;
    }

    /**
     * 设置是否为签到景点
     * 0：否
     * 1：是
     */
    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    /**
     * 获取景点图片
     * @return
     */
    public List<String> getScenicImg() {
        return scenicImg;
    }

    /**
     * 设置景点图片
     * @param scenicImg
     */
    public void setScenicImg(List<String> scenicImg) {
        this.scenicImg = scenicImg;
    }
}