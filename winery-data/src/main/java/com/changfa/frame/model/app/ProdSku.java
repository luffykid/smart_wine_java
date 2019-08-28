/*
 * ProdSku.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品sku表
 * @version 1.0 2019-08-22
 */
public class ProdSku extends BaseEntity {

    private static final long serialVersionUID = 444291406656176128L;

    /** 商品ID */
    private Long prodId;

    /** 商品sku名称 */
    private String skuName;

    /** sku状态
    0：未上架
    1：已上架 */
    private Integer skuStatus;

    public enum SKU_STATUS_ENUM {
        WSJ(0, "未上架"),
        YSJ(1, "已上架");


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
        SKU_STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         */
        public static SKU_STATUS_ENUM getEnum(Integer value) {
            for (SKU_STATUS_ENUM queryDayEnum : SKU_STATUS_ENUM.values()) {
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

    /** 库存 */
    private Long skuStockCnt;

    /** sku重量（单位：g） */
    private BigDecimal skuWeight;

    /** sku容量（单位：ml） */
    private BigDecimal skuCapacity;

    /** sku销售价 */
    private BigDecimal skuSellPrice;

    /** sku市场价 */
    private BigDecimal skuMarketPrice;

    /** 销售总数量 */
    private Integer sellTotalCnt;

    /** 排序 */
    private Integer sort;

    /** 是否支持积分购买 */
    private Boolean isIntegral;

    /** 积分金额 */
    private BigDecimal integralAmt;

    /** 积分数量 */
    private BigDecimal integralCnt;

    /** 香型*/
    private String fragranceType;

    /**酒精度*/
    private BigDecimal alcoholCnt;

    /**是否删除
     0：否
     1：是*/
    private Boolean isDel;

    /**
     * 产品规格会员价对象
     */
    private List<ProdSkuMbrPrice> prodSkuMbrPriceList;

    /**酒庄名称*/
    private String prodName;

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
     * 获取商品sku名称
    */
    public String getSkuName() {
        return skuName;
    }
    
    /**
     * 设置商品sku名称
    */
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    /**
     * 获取sku状态
    0：未上架
    1：已上架
    */
    public Integer getSkuStatus() {
        return skuStatus;
    }
    
    /**
     * 设置sku状态
    0：未上架
    1：已上架
    */
    public void setSkuStatus(Integer skuStatus) {
        this.skuStatus = skuStatus;
    }
    
    /**
     * 获取库存
    */
    public Long getSkuStockCnt() {
        return skuStockCnt;
    }
    
    /**
     * 设置库存
    */
    public void setSkuStockCnt(Long skuStockCnt) {
        this.skuStockCnt = skuStockCnt;
    }
    
    /**
     * 获取sku重量（单位：g）
    */
    public BigDecimal getSkuWeight() {
        return skuWeight;
    }
    
    /**
     * 设置sku重量（单位：g）
    */
    public void setSkuWeight(BigDecimal skuWeight) {
        this.skuWeight = skuWeight;
    }
    
    /**
     * 获取sku容量（单位：ml）
    */
    public BigDecimal getSkuCapacity() {
        return skuCapacity;
    }
    
    /**
     * 设置sku容量（单位：ml）
    */
    public void setSkuCapacity(BigDecimal skuCapacity) {
        this.skuCapacity = skuCapacity;
    }
    
    /**
     * 获取sku销售价
    */
    public BigDecimal getSkuSellPrice() {
        return skuSellPrice;
    }
    
    /**
     * 设置sku销售价
    */
    public void setSkuSellPrice(BigDecimal skuSellPrice) {
        this.skuSellPrice = skuSellPrice;
    }
    
    /**
     * 获取sku市场价
    */
    public BigDecimal getSkuMarketPrice() {
        return skuMarketPrice;
    }
    
    /**
     * 设置sku市场价
    */
    public void setSkuMarketPrice(BigDecimal skuMarketPrice) {
        this.skuMarketPrice = skuMarketPrice;
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
    
    /**
     * 获取是否支持积分购买
    */
    public Boolean getIsIntegral() {
        return isIntegral;
    }
    
    /**
     * 设置是否支持积分购买
    */
    public void setIsIntegral(Boolean isIntegral) {
        this.isIntegral = isIntegral;
    }
    
    /**
     * 获取积分金额
    */
    public BigDecimal getIntegralAmt() {
        return integralAmt;
    }
    
    /**
     * 设置积分金额
    */
    public void setIntegralAmt(BigDecimal integralAmt) {
        this.integralAmt = integralAmt;
    }
    
    /**
     * 获取积分数量
    */
    public BigDecimal getIntegralCnt() {
        return integralCnt;
    }
    
    /**
     * 设置积分数量
    */
    public void setIntegralCnt(BigDecimal integralCnt) {
        this.integralCnt = integralCnt;
    }

    /**
     * 获取香型
     */
    public String getFragranceType() {
        return fragranceType;
    }

    /**
     * 设置香型
     */
    public void setFragranceType(String fragranceType) {
        this.fragranceType = fragranceType;
    }

    /**
     * 获取酒精度
     */
    public BigDecimal getAlcoholCnt() {
        return alcoholCnt;
    }

    /**
     * 设置酒精度
     */
    public void setAlcoholCnt(BigDecimal alcoholCnt) {
        this.alcoholCnt = alcoholCnt;
    }

    /**是否删除
     0：否
     1：是*/
    public Boolean getDel() {
        return isDel;
    }

    /**是否删除
     0：否
     1：是*/
    public void setDel(Boolean del) {
        isDel = del;
    }

    /**
     * 获取产品规格会员价对象
     */
    public List<ProdSkuMbrPrice> getProdSkuMbrPriceList() {
        return prodSkuMbrPriceList;
    }

    /**
     * 设置产品规格会员价对象
     */
    public void setProdSkuMbrPriceList(List<ProdSkuMbrPrice> prodSkuMbrPriceList) {
        this.prodSkuMbrPriceList = prodSkuMbrPriceList;
    }

    /**
     * 获取酒庄名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 设置酒庄名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}