/*
 * WineCellarActivityLike.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-28 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 云酒窖活动点赞表
 * @version 1.0 2019-08-28
 */
public class WineCellarActivityLike extends BaseEntity {

    private static final long serialVersionUID = 446531991521722368L;

    /** 云酒窖活动id */
    private Long wineCellarActivityId;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 点赞状态0：取消点赞1：已点赞 */
    private Integer likeStatus;

    
    /**
     * 获取云酒窖活动id
    */
    public Long getWineCellarActivityId() {
        return wineCellarActivityId;
    }
    
    /**
     * 设置云酒窖活动id
    */
    public void setWineCellarActivityId(Long wineCellarActivityId) {
        this.wineCellarActivityId = wineCellarActivityId;
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
     * 获取点赞状态0：取消点赞1：已点赞
    */
    public Integer getLikeStatus() {
        return likeStatus;
    }
    
    /**
     * 设置点赞状态0：取消点赞1：已点赞
    */
    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }
}