/*
 * WineryAdjustAdvance.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒定制预置图
 * @version 1.0 2019-08-27
 */
public class WineryAdjustAdvance extends BaseEntity {

    private static final long serialVersionUID = 446082036856258560L;

    /** 酒庄自调酒订单 */
    private Long wineryAdjustWineId;

    /** 预置图名称 */
    private String advanceName;

    /** 预置图地址 */
    private String advanceImg;

    /** 预置图备注
 */
    private String advanceRemark;

    /** 预置图状态0：新建1：启用2：禁用 */
    private Integer status;
    public enum STATUS_ENUM {
        XJ(0, "新建"),
        QY(1, "启用"),
        JY(2, "禁用");

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
        STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         */
        public static WineryAdjustAdvance.STATUS_ENUM getEnum(Integer value) {
            for (WineryAdjustAdvance.STATUS_ENUM queryDayEnum : WineryAdjustAdvance.STATUS_ENUM.values()) {
                if (value.equals(queryDayEnum.getValue())) {
                    return queryDayEnum;
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

    /** 排序 */
    private Integer sort;

    
    /**
     * 获取酒庄自调酒订单
    */
    public Long getWineryAdjustWineId() {
        return wineryAdjustWineId;
    }
    
    /**
     * 设置酒庄自调酒订单
    */
    public void setWineryAdjustWineId(Long wineryAdjustWineId) {
        this.wineryAdjustWineId = wineryAdjustWineId;
    }
    
    /**
     * 获取预置图名称
    */
    public String getAdvanceName() {
        return advanceName;
    }
    
    /**
     * 设置预置图名称
    */
    public void setAdvanceName(String advanceName) {
        this.advanceName = advanceName == null ? null : advanceName.trim();
    }
    
    /**
     * 获取预置图地址
    */
    public String getAdvanceImg() {
        return advanceImg;
    }
    
    /**
     * 设置预置图地址
    */
    public void setAdvanceImg(String advanceImg) {
        this.advanceImg = advanceImg == null ? null : advanceImg.trim();
    }
    
    /**
     * 获取预置图备注

    */
    public String getAdvanceRemark() {
        return advanceRemark;
    }
    
    /**
     * 设置预置图备注

    */
    public void setAdvanceRemark(String advanceRemark) {
        this.advanceRemark = advanceRemark == null ? null : advanceRemark.trim();
    }
    
    /**
     * 获取预置图状态0：新建1：启用2：禁用
    */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * 设置预置图状态0：新建1：启用2：禁用
    */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取排序
    */
    public Integer getSort() {
        return sort;
    }
    
    /**
     * 设置排序
    */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}