/*
 * MbrSightSign.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 会员景点签到
 *
 * @version 1.0 2019-08-14
 */
public class MbrSightSign extends BaseEntity {

    private static final long serialVersionUID = 441546301381804032L;

    /**
     * 酒庄景点ID
     */
    private Long winerySightId;

    /**
     * 会员ID
     */
    private Long mberId;

    /**
     * 签到时间
     */
    private Date signDate;


    /**
     * 获取酒庄景点ID
     */
    public Long getWinerySightId() {
        return winerySightId;
    }

    /**
     * 设置酒庄景点ID
     */
    public void setWinerySightId(Long winerySightId) {
        this.winerySightId = winerySightId;
    }

    /**
     * 获取会员ID
     */
    public Long getMberId() {
        return mberId;
    }

    /**
     * 设置会员ID
     */
    public void setMberId(Long mberId) {
        this.mberId = mberId;
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