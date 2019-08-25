/*
 * Admin.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 系统用户
 *
 * @version 1.0 2019-08-26
 */
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 445632600640323584L;

    private Long wineryId;

    /**
     * 登录用户
     */
    private String loginName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     * 0：禁用
     * 1：启用
     */
    private Integer status;

    /**
     * 登录失败次数
     */
    private Integer loginFailCnt;

    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 锁定时间
     */
    private Date lockedDate;


    public Long getWineryId() {
        return wineryId;
    }

    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }

    /**
     * 获取登录用户
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录用户
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * 获取用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取登录密码
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * 设置登录密码
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    /**
     * 获取手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取状态
     * 0：禁用
     * 1：启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     * 0：禁用
     * 1：启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取登录失败次数
     */
    public Integer getLoginFailCnt() {
        return loginFailCnt;
    }

    /**
     * 设置登录失败次数
     */
    public void setLoginFailCnt(Integer loginFailCnt) {
        this.loginFailCnt = loginFailCnt;
    }

    /**
     * 获取是否锁定
     */
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * 设置是否锁定
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * 获取锁定时间
     */
    public Date getLockedDate() {
        return lockedDate;
    }

    /**
     * 设置锁定时间
     */
    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }
}