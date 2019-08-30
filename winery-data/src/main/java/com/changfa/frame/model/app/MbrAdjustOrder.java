/*
 * MbrAdjustOrder.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员自调酒订单
 *
 * @version 1.0 2019-08-31
 */
public class MbrAdjustOrder extends BaseEntity {

    private static final long serialVersionUID = 447405241634127872L;

    /**
     * 酒庄自调酒id
     */
    private Long wineryAdjustWineId;

    /**
     * 会员id
     */
    private Long mbrId;

    /**
     * 酒庄id
     */
    private Long wineryId;

    /**
     * 支付方式
     * 1：微信支付
     */
    private Integer payMode;

    /**
     * 支付总金额
     */
    private BigDecimal payTotalAmt;

    /**
     * 实际总价格
     */
    private BigDecimal payRealAmt;

    /**
     * 打印图
     */
    private String printImg;

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
     * 支付时间
     */
    private Date payDate;


    /**
     * 获取酒庄自调酒id
     */
    public Long getWineryAdjustWineId() {
        return wineryAdjustWineId;
    }

    /**
     * 设置酒庄自调酒id
     */
    public void setWineryAdjustWineId(Long wineryAdjustWineId) {
        this.wineryAdjustWineId = wineryAdjustWineId;
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
     * 获取支付方式
     * 1：微信支付
     */
    public Integer getPayMode() {
        return payMode;
    }

    /**
     * 设置支付方式
     * 1：微信支付
     */
    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
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
     * 获取打印图
     */
    public String getPrintImg() {
        return printImg;
    }

    /**
     * 设置打印图
     */
    public void setPrintImg(String printImg) {
        this.printImg = printImg == null ? null : printImg.trim();
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