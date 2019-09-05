/*
 * WineCustomElement.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒定制元素
 *
 * @version 1.0 2019-08-26
 */
public class WineCustomElement extends BaseEntity {

    private static final long serialVersionUID = 445866534246547456L;
    /**
     * 酒庄id
     */
    private Long wineryId;

    /**
     * 元素名称
     */
    private String elementName;

    /**
     * 元素编码
     */
    private String elementCode;

    /**
     * 元素icon
     */
    private String elementIcon;

    /**
     * 元素状态
     * 0：新建
     * 1：启用
     * 2：禁用
     */
    private Integer elementStatus;

    public enum ELEMENT_STATUS_ENUM {
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
        ELEMENT_STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         */
        public static ELEMENT_STATUS_ENUM getEnum(Integer value) {
            for (ELEMENT_STATUS_ENUM queryDayEnum : ELEMENT_STATUS_ENUM.values()) {
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

    /**
     * 元素排序
     */
    private Integer sort;

    /**
     * 获取元素名称
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * 设置元素名称
     */
    public void setElementName(String elementName) {
        this.elementName = elementName == null ? null : elementName.trim();
    }

    /**
     * 获取元素编码
     */
    public String getElementCode() {
        return elementCode;
    }

    /**
     * 设置元素编码
     */
    public void setElementCode(String elementCode) {
        this.elementCode = elementCode == null ? null : elementCode.trim();
    }

    /**
     * 获取元素icon
     */
    public String getElementIcon() {
        return elementIcon;
    }

    /**
     * 设置元素icon
     */
    public void setElementIcon(String elementIcon) {
        this.elementIcon = elementIcon == null ? null : elementIcon.trim();
    }

    /**
     * 获取元素状态
     * 0：新建
     * 1：启用
     * 2：禁用
     */
    public Integer getElementStatus() {
        return elementStatus;
    }

    /**
     * 设置元素状态
     * 0：新建
     * 1：启用
     * 2：禁用
     */
    public void setElementStatus(Integer elementStatus) {
        this.elementStatus = elementStatus;
    }

    /**
     * 获取元素排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置元素排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
}