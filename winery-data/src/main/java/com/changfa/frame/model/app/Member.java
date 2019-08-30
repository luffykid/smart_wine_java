/*
 * Member.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

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
     * 昵称
     */
    private String mbrName;

    /**
     * 手机号
     */
    private String phone;

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
     * 用户状态枚举
     */
    public enum STATUS_ENUM {
        DISABLE(0, "禁用"),
        NORMAL(1, "正常");

        /**
         * 枚举值
         */
        private Integer value;

        /**
         * 枚举名称
         */
        private String name;

        /**
         * 枚举有参构造函数
         *
         * @param value 枚举值
         * @param name  枚举名
         */
        STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         * @return
         */
        public static Member.STATUS_ENUM getEnum(Integer value) {
            for (Member.STATUS_ENUM statusEnum : Member.STATUS_ENUM.values()) {
                if (value.equals(statusEnum.getValue())) {
                    return statusEnum;
                }
            }
            return null;
        }

        /**
         * 获取枚举值
         */
        public Integer getValue() {
            return value;
        }

        /**
         * 获取枚举名
         */
        public String getName() {
            return name;
        }
    }

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

    /**
     * 账户余额
     */
    public BigDecimal acctBalance;

    /**
     * 年龄
     */
    public Integer age;

    /**
     * 性别
     */
    public Integer gender;

    /**
     * 返现金额
     */
    public BigDecimal returnAmt;

    /******************** 扩展属性 ***********************/
    /**
     * 优惠券数量
     */
    public Integer voucherCount;

    /**
     * 会员微信昵称
     */
    public String nickName;

    /**
     * 会员等级名称
     */
    public String levelName;

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
     * 获取昵称
     */
    public String getMbrName() {
        return mbrName;
    }

    /**
     * 设置昵称
     */
    public void setMbrName(String nickName) {
        this.mbrName = nickName == null ? null : nickName.trim();
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

    /**
     * 获取优惠券数量
     */
    public Integer getVoucherCount() {
        return voucherCount;
    }

    /**
     * 设置优惠券数量
     */
    public void setVoucherCount(Integer voucherCount) {
        this.voucherCount = voucherCount;
    }

    /**
     * 获取账户余额
     */
    public BigDecimal getAcctBalance() {
        return acctBalance;
    }

    /**
     * 设置账户余额
     */
    public void setAcctBalance(BigDecimal acctBalance) {
        this.acctBalance = acctBalance;
    }

    /**
     * 获取年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取性别
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 返现金额
     */
    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    /**
     * 返现金额
     */
    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    /**
     * 获取会员微信昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置会员微信昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取会员等级名称
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * 设置会员等级名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}