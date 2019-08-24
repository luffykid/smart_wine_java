/*
 * Prod.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-22 Created
 */
package com.changfa.frame.model.app;

import com.changfa.frame.model.common.BaseEntity;

import java.util.List;

/**
 * 商品表
 * @version 1.0 2019-08-22
 */
public class Prod extends BaseEntity {

    private static final long serialVersionUID = 444289520259563520L;

    /** 酒庄ID */
    private Long wineryId;

    /** 商品类目ID */
    private Integer prodCategoryId;

    /** 标签类型
    1：普通酒
    2：封坛酒 */
    private Integer lableType;

    /** 商品名称 */
    private String prodName;

    /** 商品标题 */
    private String prodTitle;

    /** 商品编码 */
    private String prodCode;

    /** 总库存 */
    private Long totalStockCnt;

    /** 列表图 */
    private String listImg;

    /** 商品介绍 */
    private String descri;

    /** 是否限购
    0：否
    1：是 */
    private Boolean isLimit;

    /** 限购数量 */
    private Integer limitCount;

    /** 状态
    0：禁用
    1：启用 */
    private Integer prodStatus;

    /** 是否推荐
    0：否
    1：是 */
    private Boolean isRecommend;

    /** 创建人 */
    private Long adminId;

    /**是否删除
     0：否
     1：是*/
    private Boolean isDel;

    /**
     * 产品详情
     */
    private List<ProdDetail> prodDetailList;
    
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
     * 获取商品类目ID
    */
    public Integer getProdCategoryId() {
        return prodCategoryId;
    }
    
    /**
     * 设置商品类目ID
    */
    public void setProdCategoryId(Integer prodCategoryId) {
        this.prodCategoryId = prodCategoryId;
    }
    
    /**
     * 获取标签类型
    1：普通酒
    2：封坛酒
    */
    public Integer getLableType() {
        return lableType;
    }
    
    /**
     * 设置标签类型
    1：普通酒
    2：封坛酒
    */
    public void setLableType(Integer lableType) {
        this.lableType = lableType;
    }
    
    /**
     * 获取商品名称
    */
    public String getProdName() {
        return prodName;
    }
    
    /**
     * 设置商品名称
    */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }
    
    /**
     * 获取商品标题
    */
    public String getProdTitle() {
        return prodTitle;
    }
    
    /**
     * 设置商品标题
    */
    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle == null ? null : prodTitle.trim();
    }
    
    /**
     * 获取商品编码
    */
    public String getProdCode() {
        return prodCode;
    }
    
    /**
     * 设置商品编码
    */
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode == null ? null : prodCode.trim();
    }
    
    /**
     * 获取总库存
    */
    public Long getTotalStockCnt() {
        return totalStockCnt;
    }
    
    /**
     * 设置总库存
    */
    public void setTotalStockCnt(Long totalStockCnt) {
        this.totalStockCnt = totalStockCnt;
    }
    
    /**
     * 获取列表图
    */
    public String getListImg() {
        return listImg;
    }
    
    /**
     * 设置列表图
    */
    public void setListImg(String listImg) {
        this.listImg = listImg == null ? null : listImg.trim();
    }
    
    /**
     * 获取商品介绍
    */
    public String getDescri() {
        return descri;
    }
    
    /**
     * 设置商品介绍
    */
    public void setDescri(String descri) {
        this.descri = descri == null ? null : descri.trim();
    }
    
    /**
     * 获取是否限购
    0：否
    1：是
    */
    public Boolean getIsLimit() {
        return isLimit;
    }
    
    /**
     * 设置是否限购
    0：否
    1：是
    */
    public void setIsLimit(Boolean isLimit) {
        this.isLimit = isLimit;
    }
    
    /**
     * 获取限购数量
    */
    public Integer getLimitCount() {
        return limitCount;
    }
    
    /**
     * 设置限购数量
    */
    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }
    
    /**
     * 获取状态
    0：禁用
    1：启用
    */
    public Integer getProdStatus() {
        return prodStatus;
    }
    
    /**
     * 设置状态
    0：禁用
    1：启用
    */
    public void setProdStatus(Integer prodStatus) {
        this.prodStatus = prodStatus;
    }
    
    /**
     * 获取是否推荐
    0：否
    1：是
    */
    public Boolean getIsRecommend() {
        return isRecommend;
    }
    
    /**
     * 设置是否推荐
    0：否
    1：是
    */
    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }
    
    /**
     * 获取创建人
    */
    public Long getAdminId() {
        return adminId;
    }
    
    /**
     * 设置创建人
    */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * 获取产品详情
     */
    public List<ProdDetail> getProdDetailList() {
        return prodDetailList;
    }

    /**
     * 设置产品详情
     */
    public void setProdDetailList(List<ProdDetail> prodDetailList) {
        this.prodDetailList = prodDetailList;
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
}