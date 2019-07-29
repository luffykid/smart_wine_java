package com.changfa.frame.data.dto.saas;

/**
 * Created by Administrator on 2018/11/20.
 */
public class ProdListDTO {
    private Integer prodId;
    private String prodLogo;
    private String code;
    private String prodName;
    private String prodCataName;
    private String prodBrandName;
    private Integer stock;
    private String status;
    private String isTui;
    private String isLimit;

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdLogo() {
        return prodLogo;
    }

    public void setProdLogo(String prodLogo) {
        this.prodLogo = prodLogo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCataName() {
        return prodCataName;
    }

    public void setProdCataName(String prodCataName) {
        this.prodCataName = prodCataName;
    }

    public String getProdBrandName() {
        return prodBrandName;
    }

    public void setProdBrandName(String prodBrandName) {
        this.prodBrandName = prodBrandName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsTui() {
        return isTui;
    }

    public void setIsTui(String isTui) {
        this.isTui = isTui;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }
}
