/*
 * WineryActivity.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-30 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 酒庄活动
 * @version 1.0 2019-08-30
 */
public class WineryActivity extends BaseEntity {

    private static final long serialVersionUID = 447274608001810432L;

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

    /** 是否可报名0：不可报名1：可报名 */
    private Boolean isSign;

    /** 活动省份ID */
    private Long activityProvinceId;

    /** 活动市ID */
    private Long activityCityId;

    /** 活动县ID */
    private Long activityCountyId;

    /** 活动详细地址 */
    private String activityDetailAddr;

    /** 活动地址全称 */
    private String activityFullAddr;


    /******************************* 扩展字段*********************************/
    /** 点赞状态0:为点赞 1:点赞**/
    private Integer likeStatus;

    /**会员活动状态 0:未报名 1：已报名 2：已签到 3：已签退 **/
    private Integer mbrActStatus;


    public Integer getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }

    public Integer getMbrActStatus() {
        return mbrActStatus;
    }

    public void setMbrActStatus(Integer mbrActStatus) {
        this.mbrActStatus = mbrActStatus;
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
    
    /**
     * 获取是否可报名0：不可报名1：可报名
    */
    public Boolean getIsSign() {
        return isSign;
    }
    
    /**
     * 设置是否可报名0：不可报名1：可报名
    */
    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }
    
    /**
     * 获取活动省份ID
    */
    public Long getActivityProvinceId() {
        return activityProvinceId;
    }
    
    /**
     * 设置活动省份ID
    */
    public void setActivityProvinceId(Long activityProvinceId) {
        this.activityProvinceId = activityProvinceId;
    }
    
    /**
     * 获取活动市ID
    */
    public Long getActivityCityId() {
        return activityCityId;
    }
    
    /**
     * 设置活动市ID
    */
    public void setActivityCityId(Long activityCityId) {
        this.activityCityId = activityCityId;
    }
    
    /**
     * 获取活动县ID
    */
    public Long getActivityCountyId() {
        return activityCountyId;
    }
    
    /**
     * 设置活动县ID
    */
    public void setActivityCountyId(Long activityCountyId) {
        this.activityCountyId = activityCountyId;
    }
    
    /**
     * 获取活动详细地址
    */
    public String getActivityDetailAddr() {
        return activityDetailAddr;
    }
    
    /**
     * 设置活动详细地址
    */
    public void setActivityDetailAddr(String activityDetailAddr) {
        this.activityDetailAddr = activityDetailAddr == null ? null : activityDetailAddr.trim();
    }
    
    /**
     * 获取活动地址全称
    */
    public String getActivityFullAddr() {
        return activityFullAddr;
    }
    
    /**
     * 设置活动地址全称
    */
    public void setActivityFullAddr(String activityFullAddr) {
        this.activityFullAddr = activityFullAddr == null ? null : activityFullAddr.trim();
    }
}