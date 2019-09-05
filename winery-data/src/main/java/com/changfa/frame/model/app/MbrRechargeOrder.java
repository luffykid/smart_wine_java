/*
 * MbrRechargeOrder.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0 2019-08-25
 */
public class MbrRechargeOrder extends BaseEntity {

    private static final long serialVersionUID = 445385130505142272L;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 支付总金额
     */
    private BigDecimal payTotalAmt;

    /**
     * 支付实际金额
     */
    private BigDecimal payRealAmt;


    /**
     * 交易单号【三方支付返回单号】
     */
    private String transactionNo;


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付时间

     */
    private Date payDate;

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
        public static ORDER_STATUS_ENUM getEnum(Integer value) {
            for (ORDER_STATUS_ENUM statusEnum : ORDER_STATUS_ENUM.values()) {
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
     * 获取支付实际金额
     */
    public BigDecimal getPayRealAmt() {
        return payRealAmt;
    }

    /**
     * 设置支付实际金额
     */
    public void setPayRealAmt(BigDecimal payRealAmt) {
        this.payRealAmt = payRealAmt;
    }

    /**
     * 订单状态
     * 1：未支付（已生成预支付ID）
     * 2：已取消（取消订单）
     * 3：支付成功（回调通知成功）
     * 4：支付失败（回调通知失败）
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 订单状态
     * 1：未支付（已生成预支付ID）
     * 2：已取消（取消订单）
     * 3：支付成功（回调通知成功）
     * 4：支付失败（回调通知失败）
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    /**
     * 获取交易单号【三方支付返回单号】
     */
    public String getTransactionNo() {
        return transactionNo;
    }
    /**
     * 设置交易单号【三方支付返回单号】
     */
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
    /**
     * 获取订单号
     */
    public String getOrderNo() {
        return orderNo;
    }
    /**
     * 设置订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    /**
     * 获取支付时间

     */
    public Date getPayDate() {
        return payDate;
    }
    /**
     * 设置支付时间

     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

}