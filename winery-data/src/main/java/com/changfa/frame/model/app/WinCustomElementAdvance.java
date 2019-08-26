/*
 * WinCustomElementAdvance.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

/**
 * 白酒定制元素预置图
 * @version 1.0 2019-08-26
 */
public class WinCustomElementAdvance extends BaseEntity {

    private static final long serialVersionUID = 445866763918245888L;

    /** 白酒定制元素内容ID */
    private Long winCustomElementContentId;

    /** 白酒定制预置图ID */
    private Long wineCustomAdvanceId;

    
    /**
     * 获取白酒定制元素内容ID
    */
    public Long getWinCustomElementContentId() {
        return winCustomElementContentId;
    }
    
    /**
     * 设置白酒定制元素内容ID
    */
    public void setWinCustomElementContentId(Long winCustomElementContentId) {
        this.winCustomElementContentId = winCustomElementContentId;
    }
    
    /**
     * 获取白酒定制预置图ID
    */
    public Long getWineCustomAdvanceId() {
        return wineCustomAdvanceId;
    }
    
    /**
     * 设置白酒定制预置图ID
    */
    public void setWineCustomAdvanceId(Long wineCustomAdvanceId) {
        this.wineCustomAdvanceId = wineCustomAdvanceId;
    }
}