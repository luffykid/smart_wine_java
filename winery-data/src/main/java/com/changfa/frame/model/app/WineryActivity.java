/*
 * WineryActivity.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 
 * @version 1.0 2019-08-23
 */
public class WineryActivity extends BaseEntity {

    private static final long serialVersionUID = 444644820263108608L;

    /** 酒庄ID */
    private Long wineryId;

    /** 活动主题 */
    private String actTitle;

    /** 活动封面图 */
    private String actCoverImg;

    /** 是否为平台活动0：否（酒庄活动）1：是（平台活动） */
    private Boolean isPlatform;

    /** 活动类型1：长期活动2：短期活动 */
    private Integer actType;

    /** 报名开始时间 */
    private Date signStartDate;

    /** 报名结束时间 */
    private Date signEndDate;

    /** 活动开始时间 */
    private Date actStartDate;

    /** 活动结束时间 */
    private Date actEndDate;

    /** 点赞总数 */
    private Integer likeTotalCnt;

    /** 分享总数 */
    private Integer shareTotalCnt;

    /** 报名人数 */
    private Long signTotalCnt;

    /** 活动状态1：新疆2：启用3：禁用 */
    private Integer actStatus;

    
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
     * 获取活动类型1：长期活动2：短期活动
    */
    public Integer getActType() {
        return actType;
    }
    
    /**
     * 设置活动类型1：长期活动2：短期活动
    */
    public void setActType(Integer actType) {
        this.actType = actType;
    }
    
    /**
     * 获取报名开始时间
    */
    public Date getSignStartDate() {
        return signStartDate;
    }
    
    /**
     * 设置报名开始时间
    */
    public void setSignStartDate(Date signStartDate) {
        this.signStartDate = signStartDate;
    }
    
    /**
     * 获取报名结束时间
    */
    public Date getSignEndDate() {
        return signEndDate;
    }
    
    /**
     * 设置报名结束时间
    */
    public void setSignEndDate(Date signEndDate) {
        this.signEndDate = signEndDate;
    }
    
    /**
     * 获取活动开始时间
    */
    public Date getActStartDate() {
        return actStartDate;
    }
    
    /**
     * 设置活动开始时间
    */
    public void setActStartDate(Date actStartDate) {
        this.actStartDate = actStartDate;
    }
    
    /**
     * 获取活动结束时间
    */
    public Date getActEndDate() {
        return actEndDate;
    }
    
    /**
     * 设置活动结束时间
    */
    public void setActEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
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
     * 获取报名人数
    */
    public Long getSignTotalCnt() {
        return signTotalCnt;
    }
    
    /**
     * 设置报名人数
    */
    public void setSignTotalCnt(Long signTotalCnt) {
        this.signTotalCnt = signTotalCnt;
    }
    
    /**
     * 获取活动状态1：新疆2：启用3：禁用
    */
    public Integer getActStatus() {
        return actStatus;
    }
    
    /**
     * 设置活动状态1：新疆2：启用3：禁用
    */
    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }
}