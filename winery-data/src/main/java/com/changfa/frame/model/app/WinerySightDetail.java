/*
 * WinerySightDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-14 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄景点详情
 *
 * @version 1.0 2019-08-14
 */
public class WinerySightDetail extends BaseEntity {

    private static final long serialVersionUID = 441545235839844352L;

    /**
     * 酒庄景点ID
     */
    private Long winerySightId;

    /**
     * 酒庄活动id
     */
    private Long wineryActivityId;

    /**
     * 商品SPU
     */
    private Long prodId;

    /**
     * 商品SKU
     */
    private Long prodSkuId;

    /**
     * 优惠券ID
     */
    private Long wineryVoucherId;

    /**
     * 详情主题
     */
    private String detailTitle;

    /**
     * 详情简介
     */
    private String detailBrief;

    /**
     * 详情图片
     */
    private String detailImg;

    /**
     * 详情状态
     * 0：新建
     * 1：启用
     * 2：禁用
     */
    private Integer detailStatus;

    public enum DETAIL_STATUS_ENUM {
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
        DETAIL_STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         */
        public static DETAIL_STATUS_ENUM getEnum(Integer value) {
            for (DETAIL_STATUS_ENUM queryDayEnum : DETAIL_STATUS_ENUM.values()) {
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
     * 详情排序
     */
    private Integer sort;


    /**
     * 获取酒庄景点ID
     */
    public Long getWinerySightId() {
        return winerySightId;
    }

    /**
     * 设置酒庄景点ID
     */
    public void setWinerySightId(Long winerySightId) {
        this.winerySightId = winerySightId;
    }

    /**
     * 获取酒庄活动id
     */
    public Long getWineryActivityId() {
        return wineryActivityId;
    }

    /**
     * 设置酒庄活动id
     */
    public void setWineryActivityId(Long wineryActivityId) {
        this.wineryActivityId = wineryActivityId;
    }

    /**
     * 获取商品id
     */
    public Long getProdId() {
        return prodId;
    }

    /**
     * 设置商品id
     */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取商品SKU
     */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品SKU
     */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }

    /**
     * 获取优惠券ID
     */
    public Long getWineryVoucherId() {
        return wineryVoucherId;
    }

    /**
     * 设置优惠券ID
     */
    public void setWineryVoucherId(Long wineryVoucherId) {
        this.wineryVoucherId = wineryVoucherId;
    }

    /**
     * 获取详情主题
     */
    public String getDetailTitle() {
        return detailTitle;
    }

    /**
     * 设置详情主题
     */
    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle == null ? null : detailTitle.trim();
    }

    /**
     * 获取详情简介
     */
    public String getDetailBrief() {
        return detailBrief;
    }

    /**
     * 设置详情简介
     */
    public void setDetailBrief(String detailBrief) {
        this.detailBrief = detailBrief == null ? null : detailBrief.trim();
    }

    /**
     * 获取详情图片
     */
    public String getDetailImg() {
        return detailImg;
    }

    /**
     * 设置详情图片
     */
    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg == null ? null : detailImg.trim();
    }

    /**
     * 获取详情状态
     * 0：新建
     * 1：启用
     * 2：禁用
     */
    public Integer getDetailStatus() {
        return detailStatus;
    }

    /**
     * 设置详情状态
     * 0：新建
     * 1：启用
     * 2：禁用
     */
    public void setDetailStatus(Integer detailStatus) {
        this.detailStatus = detailStatus;
    }

    /**
     * 获取详情排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置详情排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}