/*
 * WineryActivityDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 酒庄活动详情
 *
 * @version 1.0 2019-08-31
 */
public class WineryActivityDetail extends BaseEntity {

    private static final long serialVersionUID = 447641972853702656L;

    /**
     * 酒庄活动ID
     */
    private Long wineryActivityId;

    /**
     * 商品sku id
     */
    private Long prodSkuId;

    /**
     * 酒庄优惠券ID
     */
    private Long wineryVoucherId;

    /**
     * 详情标题
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
     * 排序
     */
    private Integer sort;


    /**
     * 获取酒庄活动ID
     */
    public Long getWineryActivityId() {
        return wineryActivityId;
    }

    /**
     * 设置酒庄活动ID
     */
    public void setWineryActivityId(Long wineryActivityId) {
        this.wineryActivityId = wineryActivityId;
    }

    /**
     * 获取商品sku id
     */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品sku id
     */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }

    /**
     * 获取酒庄优惠券ID
     */
    public Long getWineryVoucherId() {
        return wineryVoucherId;
    }

    /**
     * 设置酒庄优惠券ID
     */
    public void setWineryVoucherId(Long wineryVoucherId) {
        this.wineryVoucherId = wineryVoucherId;
    }

    /**
     * 获取详情标题
     */
    public String getDetailTitle() {
        return detailTitle;
    }

    /**
     * 设置详情标题
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