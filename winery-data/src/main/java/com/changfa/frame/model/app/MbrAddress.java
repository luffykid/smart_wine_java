/*
 * MemberAddress.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 会员地址表
 *
 * @version 1.0 2019-08-25
 */
public class MbrAddress extends BaseEntity {

    private static final long serialVersionUID = 445461686204235776L;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 收货人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 省份
     */
    private Long province;

    /**
     * 市ID
     */
    private Long city;

    /**
     * 县
     */
    private Long country;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 全部地址
     */
    private String fullAddress;

    /**
     * 是否详细地址0：否1：是
     */
    private Boolean isDefault;


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
     * 获取会员ID
     */
    public Long getMbrId() {
        return mbrId;
    }

    /**
     * 设置会员ID
     */
    public void setMbrId(Long mbrId) {
        this.mbrId = mbrId;
    }

    /**
     * 获取收货人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置收货人
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * 获取联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取省份
     */
    public Long getProvince() {
        return province;
    }

    /**
     * 设置省份
     */
    public void setProvince(Long province) {
        this.province = province;
    }

    /**
     * 获取市ID
     */
    public Long getCity() {
        return city;
    }

    /**
     * 设置市ID
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * 获取县
     */
    public Long getCountry() {
        return country;
    }

    /**
     * 设置县
     */
    public void setCountry(Long country) {
        this.country = country;
    }

    /**
     * 获取详细地址
     */
    public String getDetailAddress() {
        return detailAddress;
    }

    /**
     * 设置详细地址
     */
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    /**
     * 获取全部地址
     */
    public String getFullAddress() {
        return fullAddress;
    }

    /**
     * 设置全部地址
     */
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress == null ? null : fullAddress.trim();
    }

    /**
     * 获取是否详细地址0：否1：是
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否详细地址0：否1：是
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}