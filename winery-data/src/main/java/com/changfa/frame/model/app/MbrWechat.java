/*
 * MbrWechat.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * 会员微信表
 * @version 1.0 2019-08-14
 */
public class MbrWechat extends BaseEntity {

    private static final long serialVersionUID = 441543498722705408L;

    /** 用户ID */
    private Integer mbrId;

    /** 会员昵称 */
    private String nickName;

    /** 身份证号 */
    private String idNo;

    /** 会员等级ID */
    private Integer memberLevel;

    /** 会员生日 */
    private Date birthday;

    /** 性别 */
    private String sex;

    /** 年龄 */
    private Integer age;

    /** 酒庄ID（新增） */
    private Long wineryId;

    
    /**
     * 获取用户ID
    */
    public Integer getMbrId() {
        return mbrId;
    }
    
    /**
     * 设置用户ID
    */
    public void setMbrId(Integer mbrId) {
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
     * 获取身份证号
    */
    public String getIdNo() {
        return idNo;
    }
    
    /**
     * 设置身份证号
    */
    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }
    
    /**
     * 获取会员等级ID
    */
    public Integer getMemberLevel() {
        return memberLevel;
    }
    
    /**
     * 设置会员等级ID
    */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }
    
    /**
     * 获取会员生日
    */
    public Date getBirthday() {
        return birthday;
    }
    
    /**
     * 设置会员生日
    */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    /**
     * 获取性别
    */
    public String getSex() {
        return sex;
    }
    
    /**
     * 设置性别
    */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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
     * 获取酒庄ID（新增）
    */
    public Long getWineryId() {
        return wineryId;
    }
    
    /**
     * 设置酒庄ID（新增）
    */
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }
}