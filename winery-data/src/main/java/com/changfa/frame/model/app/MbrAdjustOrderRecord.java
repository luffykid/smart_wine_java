/*
 * MbrAdjustOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员自调酒订单
 *
 * @version 1.0 2019-08-31
 */
public class MbrAdjustOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 447405943114694656L;

    /**
     * 会员自调酒订单id
     */
    private Long mbrAdjustOrderId;

    /**
     * 会员id
     */
    private Long mbrId;

    /**
     * 酒庄id
     */
    private Long wineryId;

    /**
     * 支付总金额
     */
    private BigDecimal payTotalAmt;

    /**
     * 实际总价格
     */
    private BigDecimal payRealAmt;

    /**
     * 订单状态
     * 1：未支付（已生成预支付ID）
     * 2：已取消（取消订单）
     * 3：支付成功（回调通知成功）
     * 4：支付失败（回调通知失败）
     */
    private Integer orderStatus;

    /**
     * 订单状态
     */
    public enum ORDER_STATUS_ENUM {
        PAY_NOT(1, "未支付"),
        PAY_CANCEL(2, "已取消"),
        PAY_SUCCESS(3, "支付成功"),
        PAY_FAIL(4, "支付失败");

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
        ORDER_STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         * @return
         */
        public static MbrProdOrder.ORDER_STATUS_ENUM getEnum(Integer value) {
            for (MbrProdOrder.ORDER_STATUS_ENUM statusEnum : MbrProdOrder.ORDER_STATUS_ENUM.values()) {
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
     * 获取会员自调酒订单id
     */
    public Long getMbrAdjustOrderId() {
        return mbrAdjustOrderId;
    }

    /**
     * 设置会员自调酒订单id
     */
    public void setMbrAdjustOrderId(Long mbrAdjustOrderId) {
        this.mbrAdjustOrderId = mbrAdjustOrderId;
    }

    /**
     * 获取会员id
     */
    public Long getMbrId() {
        return mbrId;
    }

    /**
     * 设置会员id
     */
    public void setMbrId(Long mbrId) {
        this.mbrId = mbrId;
    }

    /**
     * 获取酒庄id
     */
    public Long getWineryId() {
        return wineryId;
    }

    /**
     * 设置酒庄id
     */
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }

    /**
     * 获取支付总金额
     */
    public BigDecimal getPayTotalAmt() {
        return payTotalAmt;
    }

    /**
     * 设置支付总金额
     */
    public void setPayTotalAmt(BigDecimal payTotalAmt) {
        this.payTotalAmt = payTotalAmt;
    }

    /**
     * 获取实际总价格
     */
    public BigDecimal getPayRealAmt() {
        return payRealAmt;
    }

    /**
     * 设置实际总价格
     */
    public void setPayRealAmt(BigDecimal payRealAmt) {
        this.payRealAmt = payRealAmt;
    }

    /**
     * 获取订单状态
     * 1：未支付（已生成预支付ID）
     * 2：已取消（取消订单）
     * 3：支付成功（回调通知成功）
     * 4：支付失败（回调通知失败）
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     * 1：未支付（已生成预支付ID）
     * 2：已取消（取消订单）
     * 3：支付成功（回调通知成功）
     * 4：支付失败（回调通知失败）
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}