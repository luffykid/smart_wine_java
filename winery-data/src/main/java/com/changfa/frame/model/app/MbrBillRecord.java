/*
 * MbrBillRecord.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * @version 1.0 2019-08-22
 */
public class MbrBillRecord extends BaseEntity {

    private static final long serialVersionUID = 444392828055846912L;

    private Long pkId;

    private Long mbrId;

    private Long wineryId;

    private Integer signType;

    private Integer billType;

    private String billRemark;

    private BigDecimal billAmt;

    
    public Long getPkId() {
        return pkId;
    }
    
    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }
    
    public Long getMbrId() {
        return mbrId;
    }
    
    public void setMbrId(Long mbrId) {
        this.mbrId = mbrId;
    }
    
    public Long getWineryId() {
        return wineryId;
    }
    
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }
    
    public Integer getSignType() {
        return signType;
    }
    
    public void setSignType(Integer signType) {
        this.signType = signType;
    }
    
    public Integer getBillType() {
        return billType;
    }
    
    public void setBillType(Integer billType) {
        this.billType = billType;
    }
    
    public String getBillRemark() {
        return billRemark;
    }
    
    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark == null ? null : billRemark.trim();
    }
    
    public BigDecimal getBillAmt() {
        return billAmt;
    }
    
    public void setBillAmt(BigDecimal billAmt) {
        this.billAmt = billAmt;
    }
}