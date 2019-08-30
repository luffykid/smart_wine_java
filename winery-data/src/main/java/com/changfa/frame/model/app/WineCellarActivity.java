/*
 * WineCellarActivity.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒庄活动
 * @version 1.0 2019-08-27
 */
public class WineCellarActivity extends BaseEntity {

    private static final long serialVersionUID = 446186275037249536L;

    /** 酒庄ID */
    private Long wineryId;

    /** 活动主题 */
    private String actTitle;

    /** 活动封面图 */
    private String actCoverImg;

    /** 活动简介 */
    private String actBreif;

    /** 活动详情图 */
    private String actDetailImg;

    /** 是否为平台活动0：否（酒庄活动）1：是（平台活动） */
    private Boolean isPlatform;

    /** 是否随时领取0：否【一定时间后领取】1：是 */
    private Integer isNow;

    /** 领取时间 */
    private Date takeDate;

    /** 点赞总数 */
    private Integer likeTotalCnt;

    /** 分享总数 */
    private Integer shareTotalCnt;

    /** 活动状态1：新建2：启用3：禁用 */
    private Integer actStatus;

    /** 赠送描述 */
    private String presentDetail;

    /** 是否增长0：否1：是 */
    private Boolean isIncrease;

    /** 增长比例 */
    private BigDecimal increaseScale;

    /******************************* 扩展字段*********************************/
    /** 点赞状态0:为点赞 1:点赞**/
     private Integer likeStatus;

    public Integer getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
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
     * 获取活动主题
    */
    public String getActTitle() {
        return actTitle;
    }
    
    /**
     * 设置活动主题
    */
    public void setActTitle(String actTitle) {
        this.actTitle = actTitle == null ? null : actTitle.trim();
    }
    
    /**
     * 获取活动封面图
    */
    public String getActCoverImg() {
        return actCoverImg;
    }
    
    /**
     * 设置活动封面图
    */
    public void setActCoverImg(String actCoverImg) {
        this.actCoverImg = actCoverImg == null ? null : actCoverImg.trim();
    }
    
    /**
     * 获取活动简介
    */
    public String getActBreif() {
        return actBreif;
    }
    
    /**
     * 设置活动简介
    */
    public void setActBreif(String actBreif) {
        this.actBreif = actBreif == null ? null : actBreif.trim();
    }
    
    /**
     * 获取活动详情图
    */
    public String getActDetailImg() {
        return actDetailImg;
    }
    
    /**
     * 设置活动详情图
    */
    public void setActDetailImg(String actDetailImg) {
        this.actDetailImg = actDetailImg == null ? null : actDetailImg.trim();
    }
    
    /**
     * 获取是否为平台活动0：否（酒庄活动）1：是（平台活动）
    */
    public Boolean getIsPlatform() {
        return isPlatform;
    }
    
    /**
     * 设置是否为平台活动0：否（酒庄活动）1：是（平台活动）
    */
    public void setIsPlatform(Boolean isPlatform) {
        this.isPlatform = isPlatform;
    }
    
    /**
     * 获取是否随时领取0：否【一定时间后领取】1：是
    */
    public Integer getIsNow() {
        return isNow;
    }
    
    /**
     * 设置是否随时领取0：否【一定时间后领取】1：是
    */
    public void setIsNow(Integer isNow) {
        this.isNow = isNow;
    }
    
    /**
     * 获取领取时间
    */
    public Date getTakeDate() {
        return takeDate;
    }
    
    /**
     * 设置领取时间
    */
    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }
    
    /**
     * 获取点赞总数
    */
    public Integer getLikeTotalCnt() {
        return likeTotalCnt;
    }
    
    /**
     * 设置点赞总数
    */
    public void setLikeTotalCnt(Integer likeTotalCnt) {
        this.likeTotalCnt = likeTotalCnt;
    }
    
    /**
     * 获取分享总数
    */
    public Integer getShareTotalCnt() {
        return shareTotalCnt;
    }
    
    /**
     * 设置分享总数
    */
    public void setShareTotalCnt(Integer shareTotalCnt) {
        this.shareTotalCnt = shareTotalCnt;
    }
    
    /**
     * 获取活动状态1：新建2：启用3：禁用
    */
    public Integer getActStatus() {
        return actStatus;
    }
    
    /**
     * 设置活动状态1：新建2：启用3：禁用
    */
    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }
    
    /**
     * 获取赠送描述
    */
    public String getPresentDetail() {
        return presentDetail;
    }
    
    /**
     * 设置赠送描述
    */
    public void setPresentDetail(String presentDetail) {
        this.presentDetail = presentDetail == null ? null : presentDetail.trim();
    }
    
    /**
     * 获取是否增长0：否1：是
    */
    public Boolean getIsIncrease() {
        return isIncrease;
    }
    
    /**
     * 设置是否增长0：否1：是
    */
    public void setIsIncrease(Boolean isIncrease) {
        this.isIncrease = isIncrease;
    }
    
    /**
     * 获取增长比例
    */
    public BigDecimal getIncreaseScale() {
        return increaseScale;
    }
    
    /**
     * 设置增长比例
    */
    public void setIncreaseScale(BigDecimal increaseScale) {
        this.increaseScale = increaseScale;
    }
}