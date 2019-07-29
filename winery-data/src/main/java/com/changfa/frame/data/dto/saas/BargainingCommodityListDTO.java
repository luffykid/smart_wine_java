package com.changfa.frame.data.dto.saas;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/14.
 */
public class BargainingCommodityListDTO {
    private Integer id;
    private String prodName;
    private Date startTime;
    private Date endTime;
    private Integer bargainingNum;
    private Integer noBargainingNum;
    private BigDecimal bargainingPrice;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBargainingNum() {
        return bargainingNum;
    }

    public void setBargainingNum(Integer bargainingNum) {
        this.bargainingNum = bargainingNum;
    }

    public Integer getNoBargainingNum() {
        return noBargainingNum;
    }

    public void setNoBargainingNum(Integer noBargainingNum) {
        this.noBargainingNum = noBargainingNum;
    }

    public BigDecimal getBargainingPrice() {
        return bargainingPrice;
    }

    public void setBargainingPrice(BigDecimal bargainingPrice) {
        this.bargainingPrice = bargainingPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
