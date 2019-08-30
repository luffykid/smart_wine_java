/*
 * MbrWineCustomDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员白酒定制明细表
 * @version 1.0 2019-08-26
 */
@ApiModel
public class MbrWineCustomDetail extends BaseEntity {

    private static final long serialVersionUID = 445923422875156480L;

    /** 酒定制ID */
    private Long wineCustomId;

    /** 酒定制元素ID */
    private Long wineCustomElementId;

    /** 会员白酒定制ID */
    private Long mbrWineCustomId;

    /** 会员ID */
    private Long mbrId;

    /** 背景图 */
    private String bgImg;

    /** 蒙版图 */
    private String maskImg;

    /** 预览图 */
    @ApiModelProperty(value = "预览图", required = true, name = "previewImg")
    private String previewImg;

    /** 底版图 */
    private String bottomImg;

    /** 打印图 */
    private String printImg;

    /**
     *  可定制元素内容id
     */
    @ApiModelProperty(value = "可定制元素内容id", required = true, name = "wineCustomElementContentId")
    private Long wineCustomElementContentId;

    public Long getWineCustomElementContentId() {
        return wineCustomElementContentId;
    }

    public void setWineCustomElementContentId(Long wineCustomElementContentId) {
        this.wineCustomElementContentId = wineCustomElementContentId;
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
     * 获取会员白酒定制ID
    */
    public Long getMbrWineCustomId() {
        return mbrWineCustomId;
    }
    
    /**
     * 设置会员白酒定制ID
    */
    public void setMbrWineCustomId(Long mbrWineCustomId) {
        this.mbrWineCustomId = mbrWineCustomId;
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
     * 获取预览图
    */
    public String getPreviewImg() {
        return previewImg;
    }
    
    /**
     * 设置预览图
    */
    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg == null ? null : previewImg.trim();
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
    
    /**
     * 获取打印图
    */
    public String getPrintImg() {
        return printImg;
    }
    
    /**
     * 设置打印图
    */
    public void setPrintImg(String printImg) {
        this.printImg = printImg == null ? null : printImg.trim();
    }
}