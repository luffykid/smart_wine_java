package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleWeichatListDTO {
    private Integer id;//拼团商品id
    private Integer assembleListId;//拼团团队Id
    private String prodLogo;
    private String prodName;
    private String prodTitle; //标题
    private Integer assemblePreson;//拼团人数
    private BigDecimal assemblePrice;//拼团价格
    private String prodPrice; //原价
    private String prodSpecName; //规格
    private Integer assemblePresonEd; //已拼团人数  （区分是同一个团队的  还是同一个商品的)
    private Date endTime;//最终活动结束的时间点
    private Integer isAssembleSuccess;//是否拼团功(1成功，0没成功)

    public Integer getAssembleListId() {
        return assembleListId;
    }

    public void setAssembleListId(Integer assembleListId) {
        this.assembleListId = assembleListId;
    }

    public Integer getIsAssembleSuccess() {
        return isAssembleSuccess;
    }

    public void setIsAssembleSuccess(Integer isAssembleSuccess) {
        this.isAssembleSuccess = isAssembleSuccess;
    }

    public Integer getAssemblePresonEd() {
        return assemblePresonEd;
    }

    public void setAssemblePresonEd(Integer assemblePresonEd) {
        this.assemblePresonEd = assemblePresonEd;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProdSpecName() {
        return prodSpecName;
    }

    public void setProdSpecName(String prodSpecName) {
        this.prodSpecName = prodSpecName;
    }

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

    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
    }

    public BigDecimal getAssemblePrice() {
        return assemblePrice;
    }

    public void setAssemblePrice(BigDecimal assemblePrice) {
        this.assemblePrice = assemblePrice;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }
}
