/*
 * WineryAdjustAdvance.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒定制预置图
 * @version 1.0 2019-08-27
 */
public class WineryAdjustAdvance extends BaseEntity {

    private static final long serialVersionUID = 446082036856258560L;

    /** 酒庄自调酒订单 */
    private Long wineryAdjustWineId;

    /** 预置图名称 */
    private String advanceName;

    /** 预置图地址 */
    private String advanceImg;

    /** 预置图备注
 */
    private String advanceRemark;

    /** 预置图状态0：新建1：启用2：禁用 */
    private Integer status;

    /** 排序 */
    private Integer sort;

    
    /**
     * 获取酒庄自调酒订单
    */
    public Long getWineryAdjustWineId() {
        return wineryAdjustWineId;
    }
    
    /**
     * 设置酒庄自调酒订单
    */
    public void setWineryAdjustWineId(Long wineryAdjustWineId) {
        this.wineryAdjustWineId = wineryAdjustWineId;
    }
    
    /**
     * 获取预置图名称
    */
    public String getAdvanceName() {
        return advanceName;
    }
    
    /**
     * 设置预置图名称
    */
    public void setAdvanceName(String advanceName) {
        this.advanceName = advanceName == null ? null : advanceName.trim();
    }
    
    /**
     * 获取预置图地址
    */
    public String getAdvanceImg() {
        return advanceImg;
    }
    
    /**
     * 设置预置图地址
    */
    public void setAdvanceImg(String advanceImg) {
        this.advanceImg = advanceImg == null ? null : advanceImg.trim();
    }
    
    /**
     * 获取预置图备注

    */
    public String getAdvanceRemark() {
        return advanceRemark;
    }
    
    /**
     * 设置预置图备注

    */
    public void setAdvanceRemark(String advanceRemark) {
        this.advanceRemark = advanceRemark == null ? null : advanceRemark.trim();
    }
    
    /**
     * 获取预置图状态0：新建1：启用2：禁用
    */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置预置图状态0：新建1：启用2：禁用
    */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取排序
    */
    public Integer getSort() {
        return sort;
    }
    
    /**
     * 设置排序
    */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}