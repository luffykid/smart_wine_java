/*
 * WineryMaster.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄庄主
 *
 * @version 1.0 2019-08-24
 */
public class WineryMaster extends BaseEntity {

    private static final long serialVersionUID = 445118539544657920L;

    /**
     * 会员ID
     */
    private Integer mbrId;

    /**
     * 酒庄id
     */
    private Long wineryId;

    /**
     * 庄主类型
     * 1：自定义
     * 2：会员庄主
     */
    private Integer masterType;

    /**
     * 庄主名称
     */
    private String masterName;

    /**
     * 庄主介绍
     */
    private String masterDetail;


    /**
     * 获取会员ID
     */
    public Integer getMbrId() {
        return mbrId;
    }

    /**
     * 设置会员ID
     */
    public void setMbrId(Integer mbrId) {
        this.mbrId = mbrId;
    }

    /**
     * 获取酒庄id
     */
    public Long getWineryId() {
        return wineryId;
    }

    /**
     * 设置酒庄id
     */
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }

    /**
     * 获取庄主类型
     * 1：自定义
     * 2：会员庄主
     */
    public Integer getMasterType() {
        return masterType;
    }

    /**
     * 设置庄主类型
     * 1：自定义
     * 2：会员庄主
     */
    public void setMasterType(Integer masterType) {
        this.masterType = masterType;
    }

    /**
     * 获取庄主名称
     */
    public String getMasterName() {
        return masterName;
    }

    /**
     * 设置庄主名称
     */
    public void setMasterName(String masterName) {
        this.masterName = masterName == null ? null : masterName.trim();
    }

    /**
     * 获取庄主介绍
     */
    public String getMasterDetail() {
        return masterDetail;
    }

    /**
     * 设置庄主介绍
     */
    public void setMasterDetail(String masterDetail) {
        this.masterDetail = masterDetail == null ? null : masterDetail.trim();
    }
}