/*
 * WineCellar.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 云酒窖
 * @version 1.0 2019-08-27
 */
public class WineCellar extends BaseEntity {

    private static final long serialVersionUID = 446146745752092672L;

    /** 酒庄景点 */
    private Long winerySightId;

    /** 酒窖名称 */
    private String cellarName;

    /** 酒窖封面图 */
    private String cellarCoverImg;

    
    /**
     * 获取酒庄景点
    */
    public Long getWinerySightId() {
        return winerySightId;
    }
    
    /**
     * 设置酒庄景点
    */
    public void setWinerySightId(Long winerySightId) {
        this.winerySightId = winerySightId;
    }
    
    /**
     * 获取酒窖名称
    */
    public String getCellarName() {
        return cellarName;
    }
    
    /**
     * 设置酒窖名称
    */
    public void setCellarName(String cellarName) {
        this.cellarName = cellarName == null ? null : cellarName.trim();
    }
    
    /**
     * 获取酒窖封面图
    */
    public String getCellarCoverImg() {
        return cellarCoverImg;
    }
    
    /**
     * 设置酒窖封面图
    */
    public void setCellarCoverImg(String cellarCoverImg) {
        this.cellarCoverImg = cellarCoverImg == null ? null : cellarCoverImg.trim();
    }
}