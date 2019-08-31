/*
 * WineCellarActivityProd.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-31 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 酒庄活动详情
 *
 * @version 1.0 2019-08-31
 */
public class WineCellarActivityProd extends BaseEntity {

    private static final long serialVersionUID = 447645852157083648L;

    /**
     * 酒庄酒窖活动ID
     */
    private Long wineCellarActivityId;

    /**
     * 商品id
     */
    private Long prodId;

    /**
     * 商品skuID
     */
    private Long prodSkuId;

    /**
     * 酒庄优惠券ID
     */
    private Long wineryVoucherId;

    /**
     * 赠送类型
     * 1：返现
     * 2：优惠券
     * 3：其他
     */
    private Integer presentType;

    /**
     * 优惠券实例ID
     */
    private Long wineryVoucherInst;

    /**
     * 返现金额
     */
    private BigDecimal returnAmt;

    /**
     * 赠送备注
     */
    private String presentRemark;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 获取酒庄酒窖活动ID
     */
    public Long getWineCellarActivityId() {
        return wineCellarActivityId;
    }

    /**
     * 设置酒庄酒窖活动ID
     */
    public void setWineCellarActivityId(Long wineCellarActivityId) {
        this.wineCellarActivityId = wineCellarActivityId;
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
     * 获取商品skuID
     */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品skuID
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
     * 获取赠送类型
     * 1：返现
     * 2：优惠券
     * 3：其他
     */
    public Integer getPresentType() {
        return presentType;
    }

    /**
     * 设置赠送类型
     * 1：返现
     * 2：优惠券
     * 3：其他
     */
    public void setPresentType(Integer presentType) {
        this.presentType = presentType;
    }

    /**
     * 获取优惠券实例ID
     */
    public Long getWineryVoucherInst() {
        return wineryVoucherInst;
    }

    /**
     * 设置优惠券实例ID
     */
    public void setWineryVoucherInst(Long wineryVoucherInst) {
        this.wineryVoucherInst = wineryVoucherInst;
    }

    /**
     * 获取返现金额
     */
    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    /**
     * 设置返现金额
     */
    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    /**
     * 获取赠送备注
     */
    public String getPresentRemark() {
        return presentRemark;
    }

    /**
     * 设置赠送备注
     */
    public void setPresentRemark(String presentRemark) {
        this.presentRemark = presentRemark == null ? null : presentRemark.trim();
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