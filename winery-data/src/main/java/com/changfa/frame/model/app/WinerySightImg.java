/*
 * WinerySightImg.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 景点图片
 *
 * @version 1.0 2019-08-14
 */
public class WinerySightImg extends BaseEntity {

    private static final long serialVersionUID = 441545569123434496L;

    /**
     * 酒庄景点ID
     */
    private Long winerySightId;

    /**
     * 图片名称
     */
    private String imgName;

    /**
     * 图片地址
     */
    private String imgAddr;

    /**
     * 图片备注
     */
    private String imgRemark;


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
     * 获取图片名称
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * 设置图片名称
     */
    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }

    /**
     * 获取图片地址
     */
    public String getImgAddr() {
        return imgAddr;
    }

    /**
     * 设置图片地址
     */
    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr == null ? null : imgAddr.trim();
    }

    /**
     * 获取图片备注
     */
    public String getImgRemark() {
        return imgRemark;
    }

    /**
     * 设置图片备注
     */
    public void setImgRemark(String imgRemark) {
        this.imgRemark = imgRemark == null ? null : imgRemark.trim();
    }
}