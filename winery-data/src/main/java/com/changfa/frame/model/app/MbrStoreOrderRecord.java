/*
 * MbrStoreOrderRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-09-02 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 会员商品订单记录
 * @version 1.0 2019-09-02
 */
public class MbrStoreOrderRecord extends BaseEntity {

    private static final long serialVersionUID = 448120558047985664L;

    /** 会员储酒订单ID */
    private Long mbrStoreOrderId;

    /** 订单状态 */
    private Integer orderStatus;

    /** 订单备注 */
    private String orderRemark;

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
        public static MbrStoreOrder.ORDER_STATUS_ENUM getEnum(Integer value) {
            for (MbrStoreOrder.ORDER_STATUS_ENUM statusEnum : MbrStoreOrder.ORDER_STATUS_ENUM.values()) {
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
     * 获取会员储酒订单ID
    */
    public Long getMbrStoreOrderId() {
        return mbrStoreOrderId;
    }
    
    /**
     * 设置会员储酒订单ID
    */
    public void setMbrStoreOrderId(Long mbrStoreOrderId) {
        this.mbrStoreOrderId = mbrStoreOrderId;
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
     * 获取订单备注
    */
    public String getOrderRemark() {
        return orderRemark;
    }
    
    /**
     * 设置订单备注
    */
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark == null ? null : orderRemark.trim();
    }
}