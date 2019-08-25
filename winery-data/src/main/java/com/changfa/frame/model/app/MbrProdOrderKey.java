/*
 * MbrProdOrderKey.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.model.app;

import java.math.BigDecimal;

public class MbrProdOrderKey {

    private Long id;

    /** 支付总金额 */
    private BigDecimal payTotalAmt;

    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 获取支付总金额
    */
    public BigDecimal getPayTotalAmt() {
        return payTotalAmt;
    }
    
    /**
     * 设置支付总金额
    */
    public void setPayTotalAmt(BigDecimal payTotalAmt) {
        this.payTotalAmt = payTotalAmt;
    }
}