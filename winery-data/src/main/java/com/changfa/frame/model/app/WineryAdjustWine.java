/*
 * WineryAdjustWine.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-03 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄自调酒
 * @version 1.0 2019-09-03
 */
public class WineryAdjustWine extends BaseEntity {

    private static final long serialVersionUID = 448698929295392768L;

    /** 自调酒名称 */
    private String adjustName;

    /** 酒庄ID */
    private Long wineryId;

    /** 总售卖数量 */
    private Integer totalSellerCnt;

    
    /**
     * 获取自调酒名称
    */
    public String getAdjustName() {
        return adjustName;
    }
    
    /**
     * 设置自调酒名称
    */
    public void setAdjustName(String adjustName) {
        this.adjustName = adjustName == null ? null : adjustName.trim();
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
     * 获取总售卖数量
    */
    public Integer getTotalSellerCnt() {
        return totalSellerCnt;
    }
    
    /**
     * 设置总售卖数量
    */
    public void setTotalSellerCnt(Integer totalSellerCnt) {
        this.totalSellerCnt = totalSellerCnt;
    }
}