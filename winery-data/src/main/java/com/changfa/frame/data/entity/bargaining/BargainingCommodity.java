package com.changfa.frame.data.entity.bargaining;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/14.
 */
@Entity
@Table(name = "bargaining_commodity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class BargainingCommodity {
    private Integer id;
    private Integer wineryId;
    private Integer prodId;
    private BigDecimal bargainingPrice;
    private Integer bargainingNum;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private Date topTime;
    private Date createTime;
    private Integer userId;
    private Integer isDelete;
    private Integer knifeNum;

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
    @Column(name = "bargaining_price")
    public BigDecimal getBargainingPrice() {
        return bargainingPrice;
    }

    public void setBargainingPrice(BigDecimal bargainingPrice) {
        this.bargainingPrice = bargainingPrice;
    }

    @Basic
    @Column(name = "bargaining_num")
    public Integer getBargainingNum() {
        return bargainingNum;
    }

    public void setBargainingNum(Integer bargainingNum) {
        this.bargainingNum = bargainingNum;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Basic
    @Column(name = "knife_num")
    public Integer getKnifeNum() {
        return knifeNum;
    }

    public void setKnifeNum(Integer knifeNum) {
        this.knifeNum = knifeNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BargainingCommodity that = (BargainingCommodity) o;

        if (id != that.id ) return false;
        if (wineryId != null ? !wineryId.equals(that.wineryId) : that.wineryId != null) return false;
        if (prodId != null ? !prodId.equals(that.prodId) : that.prodId != null) return false;
        if (bargainingPrice != null ? !bargainingPrice.equals(that.bargainingPrice) : that.bargainingPrice != null)
            return false;
        if (bargainingNum != null ? !bargainingNum.equals(that.bargainingNum) : that.bargainingNum != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (topTime != null ? !topTime.equals(that.topTime) : that.topTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;
        if (knifeNum != null ? !knifeNum.equals(that.knifeNum) : that.knifeNum != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (bargainingPrice != null ? bargainingPrice.hashCode() : 0);
        result = 31 * result + (bargainingNum != null ? bargainingNum.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (topTime != null ? topTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (knifeNum != null ? knifeNum.hashCode() : 0);
        return result;
    }
}
