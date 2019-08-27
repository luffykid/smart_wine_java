/*
 * WineCustom.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 定制酒
 * @version 1.0 2019-08-26
 */
public class WineCustom extends BaseEntity {

    private static final long serialVersionUID = 445866094628962304L;

    /** 酒庄ID */
    private Long wineryId;

    /** 商品SKU ID */
    private Long prodSkuId;

    /** 商品SKU名称 */
    private String skuName;

    /** 定制名称 */
    private String customName;

    /** 封面图 */
    private String customCoverImg;

    /** 定制说明图 */
    private String customStateImg;

    /** 定制状态
1：新建
2：发布
3：取消发布 */
    private Integer customStatus;

    /** 定制价格 */
    private BigDecimal customPrice;

    /** 销售总数量 */
    private Integer sellTotalCnt;


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

    /**
     * 获取商品SKU ID
    */
    public Long getProdSkuId() {
        return prodSkuId;
    }

    /**
     * 设置商品SKU ID
    */
    public void setProdSkuId(Long prodSkuId) {
        this.prodSkuId = prodSkuId;
    }

    /**
     * 获取商品SKU名称
    */
    public String getSkuName() {
        return skuName;
    }

    /**
     * 设置商品SKU名称
    */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**
     * 获取定制名称
    */
    public String getCustomName() {
        return customName;
    }

    /**
     * 设置定制名称
    */
    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    /**
     * 获取封面图
    */
    public String getCustomCoverImg() {
        return customCoverImg;
    }

    /**
     * 设置封面图
    */
    public void setCustomCoverImg(String customCoverImg) {
        this.customCoverImg = customCoverImg == null ? null : customCoverImg.trim();
    }

    /**
     * 获取定制说明图
    */
    public String getCustomStateImg() {
        return customStateImg;
    }

    /**
     * 设置定制说明图
    */
    public void setCustomStateImg(String customStateImg) {
        this.customStateImg = customStateImg == null ? null : customStateImg.trim();
    }

    /**
     * 获取定制状态
1：新建
2：发布
3：取消发布
    */
    public Integer getCustomStatus() {
        return customStatus;
    }

    /**
     * 设置定制状态
1：新建
2：发布
3：取消发布
    */
    public void setCustomStatus(Integer customStatus) {
        this.customStatus = customStatus;
    }

    /**
     * 获取定制价格
    */
    public BigDecimal getCustomPrice() {
        return customPrice;
    }

    /**
     * 设置定制价格
    */
    public void setCustomPrice(BigDecimal customPrice) {
        this.customPrice = customPrice;
    }

    /**
     * 获取销售总数量
    */
    public Integer getSellTotalCnt() {
        return sellTotalCnt;
    }

    /**
     * 设置销售总数量
    */
    public void setSellTotalCnt(Integer sellTotalCnt) {
        this.sellTotalCnt = sellTotalCnt;
    }

}