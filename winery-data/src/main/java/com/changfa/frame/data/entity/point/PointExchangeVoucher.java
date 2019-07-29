package com.changfa.frame.data.entity.point;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/29.
 */
@Entity
@Table(name = "point_exchange_voucher")
public class PointExchangeVoucher {
    private Integer id;
    private Integer pointRewardRuleId;
    private Integer wineryId;
    private Integer point;
    private Integer voucherId;
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
    @Column(name = "point_reward_rule_id")
    public Integer getPointRewardRuleId() {
        return pointRewardRuleId;
    }

    public void setPointRewardRuleId(Integer pointRewardRuleId) {
        this.pointRewardRuleId = pointRewardRuleId;
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
    @Column(name = "point")
    public Integer getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Basic
    @Column(name = "voucher_id")
    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
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

        PointExchangeVoucher that = (PointExchangeVoucher) o;

        if (id != that.id) return false;
        if (wineryId != that.wineryId) return false;
        if (point != that.point) return false;
        if (voucherId != that.voucherId) return false;
        if (pointRewardRuleId != null ? !pointRewardRuleId.equals(that.pointRewardRuleId) : that.pointRewardRuleId != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (statusTime != null ? !statusTime.equals(that.statusTime) : that.statusTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId) : that.createUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pointRewardRuleId != null ? pointRewardRuleId.hashCode() : 0);
        result = 31 * result + wineryId;
        result = 31 * result + point;
        result = 31 * result + voucherId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (statusTime != null ? statusTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        return result;
    }
}
