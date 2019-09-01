/*
 * MbrSightSign.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-01 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 会员景点签到
 *
 * @version 1.0 2019-09-01
 */
public class MbrSightSign extends BaseEntity {

    private static final long serialVersionUID = 447967500139757568L;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 酒庄景点ID
     */
    private Long winerySightId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 签到时间
     */
    private Date signDate;


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