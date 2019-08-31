/*
 * MbrBillRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 会员账单记录表
 *
 * @version 1.0 2019-08-31
 */
public class MbrBillRecord extends BaseEntity {

    private static final long serialVersionUID = 447420713385590784L;

    /**
     * 外键ID
     * 账单类型
     * 1：商品消费【商品订单ID】
     * 2：储酒消费【储酒订单ID】
     * 3：自调酒消费【自调酒订单ID】
     * 4：账户充值【充值订单ID】
     * 5：账户返现【充值订单ID】
     * 6：邀请返现【邀请会员ID】
     */
    private Long pkId;

    /**
     * 会员ID
     */
    private Long mbrId;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 符号类型
     * 0：负
     * 1：正
     */
    private Integer signType;

    /**
     * 账单类型
     * 1：商品消费
     * 2：储酒消费
     * 3：自调酒消费
     * 4：账户充值
     * 5：充值返现
     * 6：邀请返现
     */
    private Integer billType;

    /**
     * 账单类型
     */
    public enum BILL_TYPE_ENUM {
        PROD_ORDER(1, "商品消费"),
        STORE_ORDER(2, "储酒消费"),
        ADJUST_ORDER(3, "自调酒消费"),
        RECHARGE_ORDER(4, "账户充值"),
        RECHARGE_RETURN(5, "充值返现"),
        INVITE_RETURN(6, "邀请返现");

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
        BILL_TYPE_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         * @return
         */
        public static BILL_TYPE_ENUM getEnum(Integer value) {
            for (BILL_TYPE_ENUM statusEnum : BILL_TYPE_ENUM.values()) {
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
     * 账单备注
     */
    private String billRemark;

    /**
     * 账单金额
     */
    private BigDecimal billAmt;


    /**
     * 获取外键ID
     * 账单类型
     * 1：商品消费【商品订单ID】
     * 2：储酒消费【储酒订单ID】
     * 3：自调酒消费【自调酒订单ID】
     * 4：账户充值【充值订单ID】
     * 5：账户返现【充值订单ID】
     * 6：邀请返现【邀请会员ID】
     */
    public Long getPkId() {
        return pkId;
    }

    /**
     * 设置外键ID
     * 账单类型
     * 1：商品消费【商品订单ID】
     * 2：储酒消费【储酒订单ID】
     * 3：自调酒消费【自调酒订单ID】
     * 4：账户充值【充值订单ID】
     * 5：充值返现【充值订单ID】
     * 6：邀请返现【邀请会员ID】
     */
    public void setPkId(Long pkId) {
        this.pkId = pkId;
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
     * 获取符号类型
     * 0：负
     * 1：正
     */
    public Integer getSignType() {
        return signType;
    }

    /**
     * 设置符号类型
     * 0：负
     * 1：正
     */
    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    /**
     * 获取账单类型
     * 1：商品消费
     * 2：储酒消费
     * 3：自调酒消费
     * 4：账户充值
     * 5：充值返现
     * 6：邀请返现
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * 设置账单类型
     * 1：商品消费
     * 2：储酒消费
     * 3：自调酒消费
     * 4：账户充值
     * 5：充值返现
     * 6：邀请返现
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    /**
     * 获取账单备注
     */
    public String getBillRemark() {
        return billRemark;
    }

    /**
     * 设置账单备注
     */
    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark == null ? null : billRemark.trim();
    }

    /**
     * 获取账单金额
     */
    public BigDecimal getBillAmt() {
        return billAmt;
    }

    /**
     * 设置账单金额
     */
    public void setBillAmt(BigDecimal billAmt) {
        this.billAmt = billAmt;
    }
}