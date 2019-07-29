package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/14.
 */
public class BargainingWeichatListDTO {
    private Integer id;    //砍价商品表id
    private String prodLogo;  //商品logo
    private String prodName;  //商品名称
    private String prodTitle; //商品标题
    private String prodSpecName; //规格
    private String prodPrice; //原价
    private BigDecimal bargainingPrice;//最低价

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdLogo() {
        return prodLogo;
    }

    public void setProdLogo(String prodLogo) {
        this.prodLogo = prodLogo;
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

    public String getProdSpecName() {
        return prodSpecName;
    }

    public void setProdSpecName(String prodSpecName) {
        this.prodSpecName = prodSpecName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public BigDecimal getBargainingPrice() {
        return bargainingPrice;
    }

    public void setBargainingPrice(BigDecimal bargainingPrice) {
        this.bargainingPrice = bargainingPrice;
    }
}
