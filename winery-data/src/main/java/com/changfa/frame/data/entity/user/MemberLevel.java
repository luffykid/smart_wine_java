package com.changfa.frame.data.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
@Entity
@Table(name = "m_member_level")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class MemberLevel {
    private Integer id;
    private Integer wineryId;
    private String name;
    private Integer upgradeExperience;
    private String descri;
    private Integer createUserId;
    private String status;
    private String statusName;
    private Date updateTime;
    private Date statusTime;
    private Date createTime;
    private String consumptionAmount;
    private String point;
    private String isFreightFree;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "upgrade_experience")
    public Integer getUpgradeExperience() {
        return upgradeExperience;
    }

    public void setUpgradeExperience(Integer upgradeExperience) {
        this.upgradeExperience = upgradeExperience;
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
    @Column(name = "create_user_id")
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    @Transient
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Transient
    public String getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(String consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }
    @Transient
    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
    @Transient
    public String getIsFreightFree() {
        return isFreightFree;
    }

    public void setIsFreightFree(String isFreightFree) {
        this.isFreightFree = isFreightFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberLevel that = (MemberLevel) o;

        if (id != that.id) return false;
        if (wineryId != that.wineryId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (upgradeExperience != null ? !upgradeExperience.equals(that.upgradeExperience) : that.upgradeExperience != null)
            return false;
        if (descri != null ? !descri.equals(that.descri) : that.descri != null) return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId) : that.createUserId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (statusTime != null ? !statusTime.equals(that.statusTime) : that.statusTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + wineryId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (upgradeExperience != null ? upgradeExperience.hashCode() : 0);
        result = 31 * result + (descri != null ? descri.hashCode() : 0);
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (statusTime != null ? statusTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
