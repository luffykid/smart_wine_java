package com.changfa.frame.data.dto.saas;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/20.
 */
public class AddProdDTO {
    private String token;
    private Integer prodId;
    private String prodName;
    private String prodTitle;
    private String prodCode;
    private Integer prodCategoryId;
    private String prodCategoryName;
    private Integer brandId;
    private String brandName;
    private Integer prodSpecGroupId;
    private String prodSpecGroupName;
    private Integer prodSpecId;
    private String prodSpecName;
    private List<String> prodLogo;
    private List<Map<String,String>> prodLogoUrl;
    private String prodDetail;
    private Integer stock;
    private String prodPrice;
    private String isLimit;//限购
    private Integer limitNum;
    private String memberDiscount;//有无会员折扣Y/N积分兑换P
    private List<ProdMemberDiscountDTO> discountItemList;//会员折扣
    private List<ProdMemberDiscountDTO> listMemberLevel;//会员折扣
    private ProdPointDetailDTO pointDetail;//积分兑换
    private String status;
    private String isHot;
    private String isPopular; // 是否热门 Y热门N不热门
    private String isSelling; // 是否热销 Y热销N不热销

    public List<Map<String, String>> getProdLogoUrl() {
        return prodLogoUrl;
    }

    public void setProdLogoUrl(List<Map<String, String>> prodLogoUrl) {
        this.prodLogoUrl = prodLogoUrl;
    }

    public List<ProdMemberDiscountDTO> getListMemberLevel() {
        return listMemberLevel;
    }

    public void setListMemberLevel(List<ProdMemberDiscountDTO> listMemberLevel) {
        this.listMemberLevel = listMemberLevel;
    }

    public String getProdCategoryName() {
        return prodCategoryName;
    }

    public void setProdCategoryName(String prodCategoryName) {
        this.prodCategoryName = prodCategoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProdSpecGroupName() {
        return prodSpecGroupName;
    }

    public void setProdSpecGroupName(String prodSpecGroupName) {
        this.prodSpecGroupName = prodSpecGroupName;
    }

    public String getProdSpecName() {
        return prodSpecName;
    }

    public void setProdSpecName(String prodSpecName) {
        this.prodSpecName = prodSpecName;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public Integer getProdCategoryId() {
        return prodCategoryId;
    }

    public void setProdCategoryId(Integer prodCategoryId) {
        this.prodCategoryId = prodCategoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProdSpecGroupId() {
        return prodSpecGroupId;
    }

    public void setProdSpecGroupId(Integer prodSpecGroupId) {
        this.prodSpecGroupId = prodSpecGroupId;
    }

    public Integer getProdSpecId() {
        return prodSpecId;
    }

    public void setProdSpecId(Integer prodSpecId) {
        this.prodSpecId = prodSpecId;
    }

    public List<String> getProdLogo() {
        return prodLogo;
    }

    public void setProdLogo(List<String> prodLogo) {
        this.prodLogo = prodLogo;
    }

    public String getProdDetail() {
        return prodDetail;
    }

    public void setProdDetail(String prodDetail) {
        this.prodDetail = prodDetail;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(String memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public List<ProdMemberDiscountDTO> getDiscountItemList() {
        return discountItemList;
    }

    public void setDiscountItemList(List<ProdMemberDiscountDTO> discountItemList) {
        this.discountItemList = discountItemList;
    }

    public ProdPointDetailDTO getPointDetail() {
        return pointDetail;
    }

    public void setPointDetail(ProdPointDetailDTO pointDetail) {
        this.pointDetail = pointDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(String isPopular) {
        this.isPopular = isPopular;
    }

    public String getIsSelling() {
        return isSelling;
    }

    public void setIsSelling(String isSelling) {
        this.isSelling = isSelling;
    }
}
