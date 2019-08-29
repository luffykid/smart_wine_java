package com.changfa.frame.website.utils;

/**
 * 订单类型枚举
 *
 * @author WYY
 * @date 2019/08/29
 */
public enum ORDER_TYPE_ENUM {
    /**
     * 枚举类型
     */
    PRODUCT_ORDER(1, "商品订单"),
    WINE_CUSTOM_ORDER(2, "定制酒订单"),
    WINE_ADJUST_ORDER(3, "自调酒订单"),
    MBR_RECHARGE_ORDER(4, "充值订单");

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 枚举名称
     */
    private String name;

    /**
     * 枚举构造函数
     *
     * @param value
     */
    ORDER_TYPE_ENUM(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据类型值获取类型名称
     *
     * @param value
     * @return 枚举名称
     */
    public String getName(Integer value) {
        // 判断值是否为空
        if (value == null) {
            return "";
        }

        // 循环取出订单类型名称
        for (ORDER_TYPE_ENUM orderTypeEnum : ORDER_TYPE_ENUM.values()) {
            if (orderTypeEnum.getValue() == value) {
                return orderTypeEnum.name();
            }
        }
        return "";
    }

    /**
     * 设置枚举名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型值
     */
    public int getValue() {
        return value;
    }

    /**
     * 设置类型值
     */
    public void setValue(int value) {
        this.value = value;
    }
}
