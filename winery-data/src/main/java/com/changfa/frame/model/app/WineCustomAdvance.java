/*
 * WineCustomAdvance.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒定制预置图
 * @version 1.0 2019-08-26
 */
public class WineCustomAdvance extends BaseEntity {

    private static final long serialVersionUID = 445866648830738432L;

    /** 定制元素ID */
    private Long wineCustomElementId;

    /** 预置图名称 */
    private String advanceName;

    /** 预置图地址 */
    private String advanceImg;

    /** 预置图备注
 */
    private String advanceRemark;

    /** 预置图状态
0：新建
1：启用
2：禁用 */
    private Integer status;

    /** 排序 */
    private Integer sort;

    
    /**
     * 获取定制元素ID
    */
    public Long getWineCustomElementId() {
        return wineCustomElementId;
    }
    
    /**
     * 设置定制元素ID
    */
    public void setWineCustomElementId(Long wineCustomElementId) {
        this.wineCustomElementId = wineCustomElementId;
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
     * 获取预置图状态
0：新建
1：启用
2：禁用
    */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置预置图状态
0：新建
1：启用
2：禁用
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