/*
 * WineCellarActivityDetail.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 商品详情
 *
 * @version 1.0 2019-08-31
 */
public class WineCellarActivityDetail extends BaseEntity {

    private static final long serialVersionUID = 447643322102579200L;

    /**
     * 云酒窖活动ID
     */
    private Long wineCellarActivity;

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

    /**
     * 详情排序
     */
    private Integer sort;


    /**
     * 获取云酒窖活动ID
     */
    public Long getWineCellarActivity() {
        return wineCellarActivity;
    }

    /**
     * 设置云酒窖活动ID
     */
    public void setWineCellarActivity(Long wineCellarActivity) {
        this.wineCellarActivity = wineCellarActivity;
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