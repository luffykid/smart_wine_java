/*
 * WineryWineProd.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 酒庄酒表
 * @version 1.0 2019-08-24
 */
public class WineryWineProd extends BaseEntity {

    private static final long serialVersionUID = 445080644335173632L;

    /** 酒庄酒ID */
    private Long wineryWineId;

    /** 酒庄ID */
    private Long wineryId;

    /** 商品ID */
    private Long prodId;

    /** 售卖总数量 */
    private Integer totalSellCnt;


    /** 酒商品简介*/
    private String wineProdBreif;

    /** 酒商品图片*/
    private String wineProdImg;

    /**
     * 扩展字段
     * 酒庄酒产品价格
     */
    private BigDecimal mbrPrice;

    /**
     * 酒庄酒产品图片
     */
    private List<ProdImg> prodImgs;

    /**
     * 酒庄酒产品相关信息
     */
    private Prod prod;

    public Prod getProd() {
        return prod;
    }

    public void setProd(Prod prod) {
        this.prod = prod;
    }

    public List<ProdImg> getProdImgs() {
        return prodImgs;
    }

    public void setProdImgs(List<ProdImg> prodImgs) {
        this.prodImgs = prodImgs;
    }

    public BigDecimal getMbrPrice() {
        return mbrPrice;
    }

    public void setMbrPrice(BigDecimal mbrPrice) {
        this.mbrPrice = mbrPrice;
    }

    /**
     * 获取酒庄酒ID
    */
    public Long getWineryWineId() {
        return wineryWineId;
    }
    
    /**
     * 设置酒庄酒ID
    */
    public void setWineryWineId(Long wineryWineId) {
        this.wineryWineId = wineryWineId;
    }
    
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
     * 获取商品ID
    */
    public Long getProdId() {
        return prodId;
    }
    
    /**
     * 设置商品ID
    */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }
    
    /**
     * 获取售卖总数量
    */
    public Integer getTotalSellCnt() {
        return totalSellCnt;
    }
    
    /**
     * 设置售卖总数量
    */
    public void setTotalSellCnt(Integer totalSellCnt) {
        this.totalSellCnt = totalSellCnt;
    }


    public String getWineProdBreif() {
        return wineProdBreif;
    }

    public void setWineProdBreif(String wineProdBreif) {
        this.wineProdBreif = wineProdBreif;
    }

    public String getWineProdImg() {
        return wineProdImg;
    }

    public void setWineProdImg(String wineProdImg) {
        this.wineProdImg = wineProdImg;
    }
}