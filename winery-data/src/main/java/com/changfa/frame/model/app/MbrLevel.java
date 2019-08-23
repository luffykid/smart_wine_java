/*
 * MbrLevel.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-21 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 会员等级表
 * @version 1.0 2019-08-21
 */
public class MbrLevel extends BaseEntity {

    private static final long serialVersionUID = 444050872062705664L;

    /** 酒庄ID */
    private Long wineryId;

    /** 等级名称 */
    private String name;

    /** 升级经验值 */
    private Integer upgradeExperience;

    /** 说明 */
    private String descri;

    /** 创建人ID */
    private Integer createUserId;

    /** 状态，A:启用，P:禁用 */
    private String status;

    /** 更新时间 */
    private String updateTime;

    /** 状态时间 */
    private Date statusTime;

    /** 创建时间 */
    private Date createTime;

    
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
     * 获取等级名称
    */
    public String getName() {
        return name;
    }
    
    /**
     * 设置等级名称
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * 获取升级经验值
    */
    public Integer getUpgradeExperience() {
        return upgradeExperience;
    }
    
    /**
     * 设置升级经验值
    */
    public void setUpgradeExperience(Integer upgradeExperience) {
        this.upgradeExperience = upgradeExperience;
    }
    
    /**
     * 获取说明
    */
    public String getDescri() {
        return descri;
    }
    
    /**
     * 设置说明
    */
    public void setDescri(String descri) {
        this.descri = descri == null ? null : descri.trim();
    }
    
    /**
     * 获取创建人ID
    */
    public Integer getCreateUserId() {
        return createUserId;
    }
    
    /**
     * 设置创建人ID
    */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    
    /**
     * 获取状态，A:启用，P:禁用
    */
    public String getStatus() {
        return status;
    }
    
    /**
     * 设置状态，A:启用，P:禁用
    */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    
    /**
     * 获取更新时间
    */
    public String getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 设置更新时间
    */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
    
    /**
     * 获取状态时间
    */
    public Date getStatusTime() {
        return statusTime;
    }
    
    /**
     * 设置状态时间
    */
    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }
    
    /**
     * 获取创建时间
    */
    public Date getCreateTime() {
        return createTime;
    }
    
    /**
     * 设置创建时间
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}