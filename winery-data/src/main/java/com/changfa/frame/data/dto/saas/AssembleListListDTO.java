package com.changfa.frame.data.dto.saas;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleListListDTO {
    private Integer id;//团队列表id
    private String assembleNo;//团购编号
    private String prodName;
    private String masterName;
    private Integer assemblePreson;
    private Integer assemblePresonNow;
    private Date createTime;
    private Integer assembleStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssembleNo() {
        return assembleNo;
    }

    public void setAssembleNo(String assembleNo) {
        this.assembleNo = assembleNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
    }

    public Integer getAssemblePresonNow() {
        return assemblePresonNow;
    }

    public void setAssemblePresonNow(Integer assemblePresonNow) {
        this.assemblePresonNow = assemblePresonNow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAssembleStatus() {
        return assembleStatus;
    }

    public void setAssembleStatus(Integer assembleStatus) {
        this.assembleStatus = assembleStatus;
    }
}
