/*
 * MbrIntegralRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-01 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员积分明细
 *
 * @version 1.0 2019-09-01
 */
public class MbrIntegralRecord extends BaseEntity {

    private static final long serialVersionUID = 448077871898951680L;

    /**
     * 外键ID
     * 账单类型
     * 1：商品消费【商品订单ID】
     * 2：储酒消费【储酒订单ID】
     * 3：自调酒消费【自调酒订单ID】
     * 4：邀请返现【商品订单ID】
     */
    private Long pkId;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 操作类型
     * 1:商品消费
     * 2:储酒消费
     * 3:自调酒消费
     * 4:账户充值
     */
    private Integer actionType;

    /**
     * 订单状态
     */
    public enum ACTION_TYPE_ENUM {
        PROD_CUSTOM(1, "商品消费"),
        STORE_CUSTOM(2, "储酒消费"),
        ADJUST_CUSTOM(3, "自调酒消费"),
        RECHARGE_ACCT(4, "账户充值");

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
        ACTION_TYPE_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
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
     * 符号类型
     * 0:负数
     * 1:正数
     */
    private Integer signType;

    /**
     * 积分值
     */
    private BigDecimal integralValue;

    /**
     * 操作后积分数
     */
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
     * 获取操作类型
     * 1:商品消费
     * 2:储酒消费
     * 3:自调酒消费
     * 4:账户充值
     */
    public Integer getActionType() {
        return actionType;
    }

    /**
     * 设置操作类型
     * 1:商品消费
     * 2:储酒消费
     * 3:自调酒消费
     * 4:账户充值
     */
    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    /**
     * 获取符号类型
     * 0:负数
     * 1:正数
     */
    public Integer getSignType() {
        return signType;
    }

    /**
     * 设置符号类型
     * 0:负数
     * 1:正数
     */
    public void setSignType(Integer signType) {
        this.signType = signType;
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

    /**
     * 获取外键
     */
    public Long getPkId() {
        return pkId;
    }

    /**
     * 设置外键
     */
    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }
}