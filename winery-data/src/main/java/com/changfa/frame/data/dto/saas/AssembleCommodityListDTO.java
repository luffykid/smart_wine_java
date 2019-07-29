package com.changfa.frame.data.dto.saas;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleCommodityListDTO {
    private Integer id;
    private String prodName;
    private BigDecimal assemblePrice;
    private Integer assembleAllnum;
    private Integer assembleNum;
    private Integer assemblePreson;
    private Integer teamNum;
    private Date startTime;
    private Date endTime;

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

    public Integer getAssembleNum() {
        return assembleNum;
    }

    public void setAssembleNum(Integer assembleNum) {
        this.assembleNum = assembleNum;
    }

    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
    }

    public Integer getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(Integer teamNum) {
        this.teamNum = teamNum;
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
}
