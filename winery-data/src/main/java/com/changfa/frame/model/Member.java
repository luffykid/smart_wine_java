/*
 * Member.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-06 Created
 */
package com.changfa.frame.model;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 
 * @version 1.0 2019-08-06
 */
public class Member extends BaseEntity {

    private static final long serialVersionUID = 438594788095164416L;

    /** 会员等级ID */
    private Long mbrLevelId;

    /** 酒庄ID */
    private Long wineryId;

    /** 令牌 */
    private String token;

    /** 昵称 */
    private String nickName;

    /** 手机号 */
    private String phone;

    /** 微信号 */
    private String wechat;

    /** 微信openId */
    private String openId;

    /** 微信头像 */
    private String userIcon;

    /** 用户状态0：禁用1：正常 */
    private Integer status;

    /** 状态时间 */
    private Date statusTime;

    
    /**
     * 获取会员等级ID
    */
    public Long getMbrLevelId() {
        return mbrLevelId;
    }
    
    /**
     * 设置会员等级ID
    */
    public void setMbrLevelId(Long mbrLevelId) {
        this.mbrLevelId = mbrLevelId;
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
     * 获取令牌
    */
    public String getToken() {
        return token;
    }
    
    /**
     * 设置令牌
    */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }
    
    /**
     * 获取昵称
    */
    public String getNickName() {
        return nickName;
    }
    
    /**
     * 设置昵称
    */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
    
    /**
     * 获取手机号
    */
    public String getPhone() {
        return phone;
    }
    
    /**
     * 设置手机号
    */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
    
    /**
     * 获取微信号
    */
    public String getWechat() {
        return wechat;
    }
    
    /**
     * 设置微信号
    */
    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }
    
    /**
     * 获取微信openId
    */
    public String getOpenId() {
        return openId;
    }
    
    /**
     * 设置微信openId
    */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
    
    /**
     * 获取微信头像
    */
    public String getUserIcon() {
        return userIcon;
    }
    
    /**
     * 设置微信头像
    */
    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }
    
    /**
     * 获取用户状态0：禁用1：正常
    */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置用户状态0：禁用1：正常
    */
    public void setStatus(Integer status) {
        this.status = status;
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
}