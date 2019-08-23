/*
 * Member.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员用户表
 *
 * @version 1.0 2019-08-14
 */
public class Member extends BaseEntity {

    private static final long serialVersionUID = 441542753134837760L;

    /**
     * 会员等级ID
     */
    private Long mbrLevelId;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 令牌
     */
    private String token;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 用户状态
     * 0：禁用
     * 1：正常
     */
    private Integer status;

    /**
     * 状态时间
     */
    private Date statusTime;

    /**
     * 总积分
     */
    private BigDecimal totalIntegral;

    /**
     * 总储酒量
     */
    private BigDecimal totalStoreRemain;

    //优惠券数量
    @Transient
    private Integer voucherCount;
    /**
     * 总储酒增量
     */
    private BigDecimal totalStoreIncrement;

    /**
     * 二维码图片地址
     */
    private String marketQrCode;

    /**
     * 营销父ID
     */
    private Long marketPid;

    public Integer getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(Integer voucherCount) {
        this.voucherCount = voucherCount;
    }

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
     * 获取用户头像
     */
    public String getUserIcon() {
        return userIcon;
    }

    /**
     * 设置用户头像
     */
    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    /**
     * 获取用户状态
     * 0：禁用
     * 1：正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置用户状态
     * 0：禁用
     * 1：正常
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

    /**
     * 获取总积分
     */
    public BigDecimal getTotalIntegral() {
        return totalIntegral;
    }

    /**
     * 设置总积分
     */
    public void setTotalIntegral(BigDecimal totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    /**
     * 获取总储酒量
     */
    public BigDecimal getTotalStoreRemain() {
        return totalStoreRemain;
    }

    /**
     * 设置总储酒量
     */
    public void setTotalStoreRemain(BigDecimal totalStoreRemain) {
        this.totalStoreRemain = totalStoreRemain;
    }

    /**
     * 获取总储酒增量
     */
    public BigDecimal getTotalStoreIncrement() {
        return totalStoreIncrement;
    }

    /**
     * 设置总储酒增量
     */
    public void setTotalStoreIncrement(BigDecimal totalStoreIncrement) {
        this.totalStoreIncrement = totalStoreIncrement;
    }

    /**
     * 获取二维码图片地址
     */
    public String getMarketQrCode() {
        return marketQrCode;
    }

    /**
     * 设置二维码图片地址
     */
    public void setMarketQrCode(String marketQrCode) {
        this.marketQrCode = marketQrCode == null ? null : marketQrCode.trim();
    }

    /**
     * 获取营销父ID
     */
    public Long getMarketPid() {
        return marketPid;
    }

    /**
     * 设置营销父ID
     */
    public void setMarketPid(Long marketPid) {
        this.marketPid = marketPid;
    }
}