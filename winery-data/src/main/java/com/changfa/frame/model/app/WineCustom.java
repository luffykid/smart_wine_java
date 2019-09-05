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
import java.util.List;

/**
 * 定制酒
 *
 * @version 1.0 2019-08-26
 */
public class WineCustom extends BaseEntity {
    /**
     * 空对象 用于selectList方法查询所有WineCustom
     */
    public static final WineCustom NULL = new WineCustom();

    private static final long serialVersionUID = 445866094628962304L;

    /**
     * 酒庄ID
     */
    private Long wineryId;

    /**
     * 商品SKU ID
     */
    private Long prodSkuId;

    /**
     * 商品SKU名称
     */
    private String skuName;

    /**
     * 定制名称
     */
    private String customName;

    /**
     * 封面图
     */
    private String customCoverImg;

    /**
     * 定制说明图
     */
    private String customStateImg;

    /**
     * 商品ID
     */
    private Long prodId;

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    /**
     * 定制状态
     * 1：新建
     * 2：发布
     * 3：取消发布
     */
    private Integer customStatus;

    public enum CUSTOM_STATUS_ENUM {
        XJ(1, "新建"),
        FB(2, "发布"),
        QXFB(3, "取消发布");


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
        CUSTOM_STATUS_ENUM(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * 根据枚举值获取枚举对象
         *
         * @param value 枚举值
         */
        public static CUSTOM_STATUS_ENUM getEnum(Integer value) {
            for (CUSTOM_STATUS_ENUM queryDayEnum : CUSTOM_STATUS_ENUM.values()) {
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

    /**
     * 定制价格
     */
    private BigDecimal customPrice;

    /**
     * 销售总数量
     */
    private Integer sellTotalCnt;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 定制元素
     */
    private String elementName;

    /**
     * 元素对象
     */
    private List<WineCustomElement> wineCustomElementList;

    /**
     * 定制元素内容对象
     */
    private List<WineCustomElementContent> wineCustomElementContentList;
    ;

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
     * 1：新建
     * 2：发布
     * 3：取消发布
     */
    public Integer getCustomStatus() {
        return customStatus;
    }

    /**
     * 设置定制状态
     * 1：新建
     * 2：发布
     * 3：取消发布
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

    /**
     * 获商品名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 设置商品名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    /**
     * 获取定制元素
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * 设置定制元素
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * 获取定制元素
     */
    public List<WineCustomElement> getWineCustomElementList() {
        return wineCustomElementList;
    }

    /**
     * 设置定制元素
     */
    public void setWineCustomElementList(List<WineCustomElement> wineCustomElementList) {
        this.wineCustomElementList = wineCustomElementList;
    }

    /**
     * 获取定制元素内容对象
     */
    public List<WineCustomElementContent> getWineCustomElementContentList() {
        return wineCustomElementContentList;
    }

    /**
     * 设置定制元素内容对象
     */
    public void setWineCustomElementContentList(List<WineCustomElementContent> wineCustomElementContentList) {
        this.wineCustomElementContentList = wineCustomElementContentList;
    }

}