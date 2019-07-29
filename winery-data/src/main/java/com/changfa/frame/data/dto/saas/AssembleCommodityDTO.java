package com.changfa.frame.data.dto.saas;

import com.changfa.frame.data.entity.prod.Prod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleCommodityDTO {
    private Integer id;
    private String token;
    private Integer prodId;
    private BigDecimal assemblePrice;
    private Integer assembleAllnum;
    private Integer assemblePreson;
    private Date startTime;
    private Date endTime;
    private Integer existTimes;

    private Prod prod;

    public Prod getProd() {
        return prod;
    }

    public void setProd(Prod prod) {
        this.prod = prod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public BigDecimal getAssemblePrice() {
        return assemblePrice;
    }

    public void setAssemblePrice(BigDecimal assemblePrice) {
        this.assemblePrice = assemblePrice;
    }

    public Integer getAssembleAllnum() {
        return assembleAllnum;
    }

    public void setAssembleAllnum(Integer assembleAllnum) {
        this.assembleAllnum = assembleAllnum;
    }

    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
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

    public Integer getExistTimes() {
        return existTimes;
    }

    public void setExistTimes(Integer existTimes) {
        this.existTimes = existTimes;
    }
}
