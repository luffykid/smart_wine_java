/*
 * MbrWineryVoucher.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.model.app;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @version 1.0 2019-08-22
 */
public class MbrWineryVoucher extends BaseEntity {

    private static final long serialVersionUID = 444381625313132544L;

    private Long wineryId;

    private Long wineryVoucherInstId;

    private Long mbrId;

    private String name;

    private Integer type;

    private BigDecimal voucherAmt;

    private BigDecimal voucherDiscount;

    private Long prodSkuId;

    private Integer enableType;

    private BigDecimal enableAmt;

    private Integer quantityLimitCnt;

    private Date effectiveBeginDate;

    private Date effectiveEndDate;

    private Integer useScope;

    private Integer voucherCnt;

    
    public Long getWineryId() {
        return wineryId;
    }
    
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }
    
    public Long getWineryVoucherInstId() {
        return wineryVoucherInstId;
    }
    
    public void setWineryVoucherInstId(Long wineryVoucherInstId) {
        this.wineryVoucherInstId = wineryVoucherInstId;
    }
    
    public Long getMbrId() {
        return mbrId;
    }
    
    public void setMbrId(Long mbrId) {
        this.mbrId = mbrId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public BigDecimal getVoucherAmt() {
        return voucherAmt;
    }
    
    public void setVoucherAmt(BigDecimal voucherAmt) {
        this.voucherAmt = voucherAmt;
    }
    
    public BigDecimal getVoucherDiscount() {
        return voucherDiscount;
    }
    
    public void setVoucherDiscount(BigDecimal voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }
    
    public Long getProdSkuId() {
        return prodSkuId;
    }
    
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }
    
    public Integer getEnableType() {
        return enableType;
    }
    
    public void setEnableType(Integer enableType) {
        this.enableType = enableType;
    }
    
    public BigDecimal getEnableAmt() {
        return enableAmt;
    }
    
    public void setEnableAmt(BigDecimal enableAmt) {
        this.enableAmt = enableAmt;
    }
    
    public Integer getQuantityLimitCnt() {
        return quantityLimitCnt;
    }
    
    public void setQuantityLimitCnt(Integer quantityLimitCnt) {
        this.quantityLimitCnt = quantityLimitCnt;
    }
    
    public Date getEffectiveBeginDate() {
        return effectiveBeginDate;
    }
    
    public void setEffectiveBeginDate(Date effectiveBeginDate) {
        this.effectiveBeginDate = effectiveBeginDate;
    }
    
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }
    
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    
    public Integer getUseScope() {
        return useScope;
    }
    
    public void setUseScope(Integer useScope) {
        this.useScope = useScope;
    }
    
    public Integer getVoucherCnt() {
        return voucherCnt;
    }
    
    public void setVoucherCnt(Integer voucherCnt) {
        this.voucherCnt = voucherCnt;
    }
}