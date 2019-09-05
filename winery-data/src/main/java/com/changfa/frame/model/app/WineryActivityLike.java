/*
 * WineryActivityLike.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-29 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄活动点赞
 * @version 1.0 2019-08-29
 */
public class WineryActivityLike extends BaseEntity {

    private static final long serialVersionUID = 446904135153876992L;

    private Long wineryActivityId;

    /** 会员ID */
    private Long mbrId;

    /** 酒庄ID */
    private Long wineryId;

    /** 点赞状态0：取消1：点赞 */
    private Integer likeStatus;

    
    public Long getWineryActivityId() {
        return wineryActivityId;
    }
    
    public void setWineryActivityId(Long wineryActivityId) {
        this.wineryActivityId = wineryActivityId;
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
     * 获取点赞状态0：取消1：点赞
    */
    public Integer getLikeStatus() {
        return likeStatus;
    }
    
    /**
     * 设置点赞状态0：取消1：点赞
    */
    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }
}