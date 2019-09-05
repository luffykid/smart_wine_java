/*
 * WinerySightMbrLike.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-28 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 景点会员点赞
 * @version 1.0 2019-08-28
 */
public class WinerySightMbrLike extends BaseEntity {

    private static final long serialVersionUID = 446466504582496256L;

    /** 会员ID */
    private Long mbrId;

    /** 点赞状态
    0：取消点赞
    1：已点赞 */
    private Integer likeStatus;

    /**景点id*/
    private Long winerySightId;

    /**酒庄id*/
    private Long wineryId;

    
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
     * 获取点赞状态
    0：取消点赞
    1：已点赞
    */
    public Integer getLikeStatus() {
        return likeStatus;
    }
    
    /**
     * 设置点赞状态
    0：取消点赞
    1：已点赞
    */
    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }

    /**
     * 获取景点ID
     */
    public Long getWinerySightId() {
        return winerySightId;
    }

    /**
     * 设置景点ID
     */
    public void setWinerySightId(Long winerySightId) {
        this.winerySightId = winerySightId;
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
}