/*
 * Area.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 区域表
 * @version 1.0 2019-08-25
 */
public class Area extends BaseEntity {

    private static final long serialVersionUID = 445461509007474688L;

    /** 名称 */
    private String name;

    /** 简称 */
    private String sname;

    /** 代码 */
    private String code;

    /** 类型 */
    private String type;

    /** 上级代码 */
    private String parentId;

    /** 排序 */
    private Integer sortPosition;

    /** 经度 */
    private BigDecimal longitude;

    /** 维度 */
    private BigDecimal latitude;

    /** 全称 */
    private String fullName;

    /** 备注 */
    private String remarks;

    
    /**
     * 获取名称
    */
    public String getName() {
        return name;
    }
    
    /**
     * 设置名称
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * 获取简称
    */
    public String getSname() {
        return sname;
    }
    
    /**
     * 设置简称
    */
    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }
    
    /**
     * 获取代码
    */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置代码
    */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    
    /**
     * 获取类型
    */
    public String getType() {
        return type;
    }
    
    /**
     * 设置类型
    */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    
    /**
     * 获取上级代码
    */
    public String getParentId() {
        return parentId;
    }
    
    /**
     * 设置上级代码
    */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
    
    /**
     * 获取排序
    */
    public Integer getSortPosition() {
        return sortPosition;
    }
    
    /**
     * 设置排序
    */
    public void setSortPosition(Integer sortPosition) {
        this.sortPosition = sortPosition;
    }
    
    /**
     * 获取经度
    */
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    /**
     * 设置经度
    */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 获取维度
    */
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    /**
     * 设置维度
    */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    /**
     * 获取全称
    */
    public String getFullName() {
        return fullName;
    }
    
    /**
     * 设置全称
    */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }
    
    /**
     * 获取备注
    */
    public String getRemarks() {
        return remarks;
    }
    
    /**
     * 设置备注
    */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}