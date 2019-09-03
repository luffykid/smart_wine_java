/*
 * MbrWineCustomOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.Date;

/**
 * @version 1.0 2019-08-26
 */
public class MbrWineCustomOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 445919378110676992L;

    /**
     * 订单ID
     */
    private Long mbrWineCustomOrderId;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单状态
     * 1：未支付（已生成预支付ID）
     * 2：已取消（取消订单）
     * 3：支付成功（回调通知成功）
     * 4：支付失败（回调通知失败）
     */
    public enum ORDER_STATUS_ENUM {

        UNPAID(1, "未支付"),
        CANCEL(2, "已取消"),
        PAY_SUCCESS(3, "支付成功"),
        PAY_FAILED(4, "支付失败");

        // 枚举值
        private Integer value;

        // 枚举值名称
        private String name;

        ORDER_STATUS_ENUM(Integer value, String name) {

            this.value = value;
            this.name = name;

        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 状态变更时间
     */
    private Date statusDate;

    /**
     * 订单状态备注
     */
    private String orderRecordRemark;


    /**
     * 获取订单ID
     */
    public Long getMbrWineCustomOrderId() {
        return mbrWineCustomOrderId;
    }

    /**
     * 设置订单ID
     */
    public void setMbrWineCustomOrderId(Long mbrWineCustomOrderId) {
        this.mbrWineCustomOrderId = mbrWineCustomOrderId;
    }

    /**
     * 获取订单状态
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取状态变更时间
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 设置状态变更时间
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 获取订单状态备注
     */
    public String getOrderRecordRemark() {
        return orderRecordRemark;
    }

    /**
     * 设置订单状态备注
     */
    public void setOrderRecordRemark(String orderRecordRemark) {
        this.orderRecordRemark = orderRecordRemark == null ? null : orderRecordRemark.trim();
    }
}