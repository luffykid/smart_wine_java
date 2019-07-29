package com.changfa.frame.data.entity.banner;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "banner")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Banner {
    private Integer id;
    private Integer wineryId;
    private Integer activityId;
    private Integer prodId;
    private Integer marketActivityId;
    private String name;
    private String type;
    private String logo;
    private String descri;
    private String status;
    private Date statusTime;
    private Date createTime;
    private Integer createUserId;

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
    @Column(name = "activity_id")
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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
    @Column(name = "market_activity_id")
    public Integer getMarketActivityId() {
        return marketActivityId;
    }

    public void setMarketActivityId(Integer marketActivityId) {
        this.marketActivityId = marketActivityId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "descri")
    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }


    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "status_time")
    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
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
    @Column(name = "create_user_id")
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return id == banner.id &&
                wineryId == banner.wineryId &&
                Objects.equals(activityId, banner.activityId) &&
                Objects.equals(prodId, banner.prodId) &&
                Objects.equals(marketActivityId, banner.marketActivityId) &&
                Objects.equals(name, banner.name) &&
                Objects.equals(type, banner.type) &&
                Objects.equals(logo, banner.logo) &&
                Objects.equals(descri, banner.descri) &&
                Objects.equals(status, banner.status) &&
                Objects.equals(statusTime, banner.statusTime) &&
                Objects.equals(createTime, banner.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, wineryId, activityId, prodId, marketActivityId, name, type, logo, descri, status, statusTime, createTime);
    }
}
