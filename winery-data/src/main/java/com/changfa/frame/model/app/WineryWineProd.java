/*
 * WineryWineProd.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄酒表
 * @version 1.0 2019-08-24
 */
public class WineryWineProd extends BaseEntity {

    private static final long serialVersionUID = 445080644335173632L;

    /** 酒庄酒ID */
    private Long wineryWineId;

    /** 酒庄ID */
    private Long wineryId;

    /** 商品ID */
    private Long prodId;

    /** 售卖总数量 */
    private Integer totalSellCnt;

    
    /**
     * 获取酒庄酒ID
    */
    public Long getWineryWineId() {
        return wineryWineId;
    }
    
    /**
     * 设置酒庄酒ID
    */
    public void setWineryWineId(Long wineryWineId) {
        this.wineryWineId = wineryWineId;
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
     * 获取商品ID
    */
    public Long getProdId() {
        return prodId;
    }
    
    /**
     * 设置商品ID
    */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }
    
    /**
     * 获取售卖总数量
    */
    public Integer getTotalSellCnt() {
        return totalSellCnt;
    }
    
    /**
     * 设置售卖总数量
    */
    public void setTotalSellCnt(Integer totalSellCnt) {
        this.totalSellCnt = totalSellCnt;
    }
}