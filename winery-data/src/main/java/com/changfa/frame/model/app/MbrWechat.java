/*
 * MbrWechat.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 会员微信表
 *
 * @version 1.0 2019-08-26
 */
public class MbrWechat extends BaseEntity {

    private static final long serialVersionUID = 445891252152434688L;

    /**
     * 用户ID
     */
    private Long mbrId;

    /**
     * 会员昵称
     */
    private String nickName;

    /**
     * 性别
     * 0：未知
     * 1：男
     * 2：女
     */
    private Integer gender;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 县
     */
    private String country;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 开放平台编号
     */
    private String unionid;

    /**
     * 生日
     */
    private Date birthday;


    /**
     * 获取用户ID
     */
    public Long getMbrId() {
        return mbrId;
    }

    /**
     * 设置用户ID
     */
    public void setMbrId(Long mbrId) {
        this.mbrId = mbrId;
    }

    /**
     * 获取会员昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置会员昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取性别
     * 0：未知
     * 1：男
     * 2：女
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别
     * 0：未知
     * 1：男
     * 2：女
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取县
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置县
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 获取头像
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置头像
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * 获取开放平台编号
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * 设置开放平台编号
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    /**
     * 获取生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}