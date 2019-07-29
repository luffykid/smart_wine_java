package com.changfa.frame.data.entity.assemble;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2018/11/14.
 */
@Entity
@Table(name = "assemble_commodity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AssembleCommodity {
    private Integer id;
    private Integer wineryId;
    private Integer prodId;
    private BigDecimal assemblePrice;
    private Integer assembleAllnum;
    private Integer assemblePreson;
    private Date startTime;
    private Date endTime;
    private Date topTime;
    private Integer existTimes;
    private Date createTime;
    private Integer userId;
    private Integer isDelete;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "winery_id")
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "prod_id")
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    @Basic
    @Column(name = "assemble_price")
    public BigDecimal getAssemblePrice() {
        return assemblePrice;
    }

    public void setAssemblePrice(BigDecimal assemblePrice) {
        this.assemblePrice = assemblePrice;
    }

    @Basic
    @Column(name = "assemble_allnum")
    public Integer getAssembleAllnum() {
        return assembleAllnum;
    }

    public void setAssembleAllnum(Integer assembleAllnum) {
        this.assembleAllnum = assembleAllnum;
    }

    @Basic
    @Column(name = "assemble_preson")
    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
    }


    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "top_time")
    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    @Basic
    @Column(name = "exist_times")
    public Integer getExistTimes() {
        return existTimes;
    }

    public void setExistTimes(Integer existTimes) {
        this.existTimes = existTimes;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "is_delete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssembleCommodity that = (AssembleCommodity) o;

        if (id != that.id ) return false;
        if (wineryId != null ? !wineryId.equals(that.wineryId) : that.wineryId != null) return false;
        if (prodId != null ? !prodId.equals(that.prodId) : that.prodId != null) return false;
        if (assemblePrice != null ? !assemblePrice.equals(that.assemblePrice) : that.assemblePrice != null)
            return false;
        if (assembleAllnum != null ? !assembleAllnum.equals(that.assembleAllnum) : that.assembleAllnum != null)
            return false;
        if (assemblePreson != null ? !assemblePreson.equals(that.assemblePreson) : that.assemblePreson != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (topTime != null ? !topTime.equals(that.topTime) : that.topTime != null) return false;
        if (existTimes != null ? !existTimes.equals(that.existTimes) : that.existTimes != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (assemblePrice != null ? assemblePrice.hashCode() : 0);
        result = 31 * result + (assembleAllnum != null ? assembleAllnum.hashCode() : 0);
        result = 31 * result + (assemblePreson != null ? assemblePreson.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (topTime != null ? topTime.hashCode() : 0);
        result = 31 * result + (existTimes != null ? existTimes.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }
}
