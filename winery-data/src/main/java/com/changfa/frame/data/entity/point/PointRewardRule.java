package com.changfa.frame.data.entity.point;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "point_reward_rule")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class PointRewardRule {
    private Integer id;
    private Integer wineryId;
    private String name;
    private String isLongTime;
    private Date beginTime;
    private Date endTime;
    private String isLimit;
    private Integer everyDayLimit;
    private BigDecimal consumeMoneyPoint;
    private BigDecimal depositMoneyPoint;
    private String descri;
    private String status;
    private Date updateTime;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "is_long_time")
    public String getIsLongTime() {
        return isLongTime;
    }

    public void setIsLongTime(String isLongTime) {
        this.isLongTime = isLongTime;
    }

    @Basic
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
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
    @Column(name = "is_limit")
    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    @Basic
    @Column(name = "every_day_limit")
    public Integer getEveryDayLimit() {
        return everyDayLimit;
    }

    public void setEveryDayLimit(Integer everyDayLimit) {
        this.everyDayLimit = everyDayLimit;
    }

    @Basic
    @Column(name = "consume_money_point")
    public BigDecimal getConsumeMoneyPoint() {
        return consumeMoneyPoint;
    }

    public void setConsumeMoneyPoint(BigDecimal consumeMoneyPoint) {
        this.consumeMoneyPoint = consumeMoneyPoint;
    }

    @Basic
    @Column(name = "deposit_money_point")
    public BigDecimal getDepositMoneyPoint() {
        return depositMoneyPoint;
    }

    public void setDepositMoneyPoint(BigDecimal depositMoneyPoint) {
        this.depositMoneyPoint = depositMoneyPoint;
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
        PointRewardRule that = (PointRewardRule) o;
        return id == that.id &&
                wineryId == that.wineryId &&
                Objects.equals(name, that.name) &&
                Objects.equals(isLongTime, that.isLongTime) &&
                Objects.equals(beginTime, that.beginTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(isLimit, that.isLimit) &&
                Objects.equals(everyDayLimit, that.everyDayLimit) &&
                Objects.equals(consumeMoneyPoint, that.consumeMoneyPoint) &&
                Objects.equals(depositMoneyPoint, that.depositMoneyPoint) &&
                Objects.equals(descri, that.descri) &&
                Objects.equals(status, that.status) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(statusTime, that.statusTime) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createUserId, that.createUserId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, wineryId, name, isLongTime, beginTime, endTime, isLimit, everyDayLimit, consumeMoneyPoint, depositMoneyPoint, descri, status, updateTime, statusTime, createTime, createUserId);
    }
}
