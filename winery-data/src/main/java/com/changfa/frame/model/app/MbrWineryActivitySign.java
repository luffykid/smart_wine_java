/*
 * MbrWineryActivitySign.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 会员酒庄活动报名表
 * @version 1.0 2019-08-23
 */
public class MbrWineryActivitySign extends BaseEntity {

    private static final long serialVersionUID = 444658659448848384L;

    /** 酒庄活动ID */
    private Long wineryActivityId;

    /** 会员ID */
    private Long mbrId;

    /** 会员活动状态1：已报名2：已签到3：已签退 */
    private Integer mbrActStatus;

    /** 报名时间 */
    private Date signUpDate;

    /** 签到时间 */
    private Date signInDate;

    /** 签退时间 */
    private Date signOffDate;

    
    /**
     * 获取酒庄活动ID
    */
    public Long getWineryActivityId() {
        return wineryActivityId;
    }
    
    /**
     * 设置酒庄活动ID
    */
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
     * 获取会员活动状态1：已报名2：已签到3：已签退
    */
    public Integer getMbrActStatus() {
        return mbrActStatus;
    }
    
    /**
     * 设置会员活动状态1：已报名2：已签到3：已签退
    */
    public void setMbrActStatus(Integer mbrActStatus) {
        this.mbrActStatus = mbrActStatus;
    }
    
    /**
     * 获取报名时间
    */
    public Date getSignUpDate() {
        return signUpDate;
    }
    
    /**
     * 设置报名时间
    */
    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }
    
    /**
     * 获取签到时间
    */
    public Date getSignInDate() {
        return signInDate;
    }
    
    /**
     * 设置签到时间
    */
    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }
    
    /**
     * 获取签退时间
    */
    public Date getSignOffDate() {
        return signOffDate;
    }
    
    /**
     * 设置签退时间
    */
    public void setSignOffDate(Date signOffDate) {
        this.signOffDate = signOffDate;
    }
}