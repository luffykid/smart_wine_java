/*
 * WinerySightSign.java
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
public class WinerySightSign extends BaseEntity {

    private static final long serialVersionUID = 438946627181346816L;

    /** 酒店景点ID */
    private Long winerySightId;

    /** 会员ID */
    private Long mbrId;

    /** 签到时间 */
    private Date signDate;

    
    /**
     * 获取酒店景点ID
    */
    public Long getWinerySightId() {
        return winerySightId;
    }
    
    /**
     * 设置酒店景点ID
    */
    public void setWinerySightId(Long winerySightId) {
        this.winerySightId = winerySightId;
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
     * 获取签到时间
    */
    public Date getSignDate() {
        return signDate;
    }
    
    /**
     * 设置签到时间
    */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }
}