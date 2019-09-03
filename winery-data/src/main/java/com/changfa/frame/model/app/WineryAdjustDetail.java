/*
 * WineryAdjustDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-03 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄自调酒详情
 * @version 1.0 2019-09-03
 */
public class WineryAdjustDetail extends BaseEntity {

    private static final long serialVersionUID = 448699618285322240L;

    /** 酒庄自调酒ID */
    private Long wineryAdjustWineId;

    /** 酒庄ID */
    private Long wineryId;

    /** 详情主题 */
    private String detailTitle;

    /** 简介 */
    private String detailBreif;

    /** 详情图片 */
    private String detailImg;

    
    /**
     * 获取酒庄自调酒ID
    */
    public Long getWineryAdjustWineId() {
        return wineryAdjustWineId;
    }
    
    /**
     * 设置酒庄自调酒ID
    */
    public void setWineryAdjustWineId(Long wineryAdjustWineId) {
        this.wineryAdjustWineId = wineryAdjustWineId;
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
    
    /**
     * 获取详情主题
    */
    public String getDetailTitle() {
        return detailTitle;
    }
    
    /**
     * 设置详情主题
    */
    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle == null ? null : detailTitle.trim();
    }
    
    /**
     * 获取简介
    */
    public String getDetailBreif() {
        return detailBreif;
    }
    
    /**
     * 设置简介
    */
    public void setDetailBreif(String detailBreif) {
        this.detailBreif = detailBreif == null ? null : detailBreif.trim();
    }
    
    /**
     * 获取详情图片
    */
    public String getDetailImg() {
        return detailImg;
    }
    
    /**
     * 设置详情图片
    */
    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg == null ? null : detailImg.trim();
    }
}