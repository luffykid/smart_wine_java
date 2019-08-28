/*
 * WineCustomElementContent.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒定制元素内容表
 * @version 1.0 2019-08-26
 */
public class WineCustomElementContent extends BaseEntity {

    private static final long serialVersionUID = 445866355334316032L;

    /** 酒定制ID */
    private Long wineCustomId;

    /** 酒定制元素ID */
    private Long wineCustomElementId;

    /** 背景图 */
    private String bgImg;

    /** 蒙版图 */
    private String maskImg;

    /** 底版图 */
    private String bottomImg;

    /** 定制元素*/
    private WineCustomElement wineCustomElement;

    /**
     * 获取定制元素
     * @return
     */
    public WineCustomElement getWineCustomElement() {
        return wineCustomElement;
    }

    /**
     * 设置定制元素
     * @param wineCustomElement
     */
    public void setWineCustomElement(WineCustomElement wineCustomElement) {
        this.wineCustomElement = wineCustomElement;
    }

    /**
     * 获取酒定制ID
    */
    public Long getWineCustomId() {
        return wineCustomId;
    }
    
    /**
     * 设置酒定制ID
    */
    public void setWineCustomId(Long wineCustomId) {
        this.wineCustomId = wineCustomId;
    }
    
    /**
     * 获取酒定制元素ID
    */
    public Long getWineCustomElementId() {
        return wineCustomElementId;
    }
    
    /**
     * 设置酒定制元素ID
    */
    public void setWineCustomElementId(Long wineCustomElementId) {
        this.wineCustomElementId = wineCustomElementId;
    }
    
    /**
     * 获取背景图
    */
    public String getBgImg() {
        return bgImg;
    }
    
    /**
     * 设置背景图
    */
    public void setBgImg(String bgImg) {
        this.bgImg = bgImg == null ? null : bgImg.trim();
    }
    
    /**
     * 获取蒙版图
    */
    public String getMaskImg() {
        return maskImg;
    }
    
    /**
     * 设置蒙版图
    */
    public void setMaskImg(String maskImg) {
        this.maskImg = maskImg == null ? null : maskImg.trim();
    }
    
    /**
     * 获取底版图
    */
    public String getBottomImg() {
        return bottomImg;
    }
    
    /**
     * 设置底版图
    */
    public void setBottomImg(String bottomImg) {
        this.bottomImg = bottomImg == null ? null : bottomImg.trim();
    }

}