/*
 * MbrIntegralDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;
import java.math.BigDecimal;

/**
 * 会员积分明细
 * @version 1.0 2019-08-26
 */
public class MbrIntegralDetail extends BaseEntity {

    private static final long serialVersionUID = 445734136712265728L;

    /** 酒庄ID */
    private Long wineryId;

    /** 会员ID */
    private Long mbrId;

    /** 操作类型1：储值2：消费3：线上抵现4：线下抵现5：兑换券6：签到7：商城消费 */
    private Integer actionType;

    /** 类型1：增加2：减少 */
    private Integer pointType;

    /** 积分值 */
    private BigDecimal integralValue;

    /** 操作后积分数 */
    private BigDecimal latestPoint;

    
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
     * 获取操作类型1：储值2：消费3：线上抵现4：线下抵现5：兑换券6：签到7：商城消费
    */
    public Integer getActionType() {
        return actionType;
    }
    
    /**
     * 设置操作类型1：储值2：消费3：线上抵现4：线下抵现5：兑换券6：签到7：商城消费
    */
    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }
    
    /**
     * 获取类型1：增加2：减少
    */
    public Integer getPointType() {
        return pointType;
    }
    
    /**
     * 设置类型1：增加2：减少
    */
    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }
    
    /**
     * 获取积分值
    */
    public BigDecimal getIntegralValue() {
        return integralValue;
    }
    
    /**
     * 设置积分值
    */
    public void setIntegralValue(BigDecimal integralValue) {
        this.integralValue = integralValue;
    }
    
    /**
     * 获取操作后积分数
    */
    public BigDecimal getLatestPoint() {
        return latestPoint;
    }
    
    /**
     * 设置操作后积分数
    */
    public void setLatestPoint(BigDecimal latestPoint) {
        this.latestPoint = latestPoint;
    }
}