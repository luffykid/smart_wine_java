package com.changfa.frame.data.entity.point;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/29.
 */
@Entity
@Table(name = "point_exchange_money")
public class PointExchangeMoney {
    private Integer id;
    private Integer pointRewardRuleId;
    private Integer wineryId;
    private Integer onlinePointToMoney;
    private Integer offlinePointToMoney;
    private String canOtherActivity;
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
    @Column(name = "online_point_to_money")
    public Integer getOnlinePointToMoney() {
        return onlinePointToMoney;
    }

    public void setOnlinePointToMoney(Integer onlinePointToMoney) {
        this.onlinePointToMoney = onlinePointToMoney;
    }

    @Basic
    @Column(name = "offline_point_to_money")
    public Integer getOfflinePointToMoney() {
        return offlinePointToMoney;
    }

    public void setOfflinePointToMoney(Integer offlinePointToMoney) {
        this.offlinePointToMoney = offlinePointToMoney;
    }

    @Basic
    @Column(name = "can_other_activity")
    public String getCanOtherActivity() {
        return canOtherActivity;
    }

    public void setCanOtherActivity(String canOtherActivity) {
        this.canOtherActivity = canOtherActivity;
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

        PointExchangeMoney that = (PointExchangeMoney) o;

        if (id != that.id) return false;
        if (wineryId != that.wineryId) return false;
        if (pointRewardRuleId != null ? !pointRewardRuleId.equals(that.pointRewardRuleId) : that.pointRewardRuleId != null)
            return false;
        if (onlinePointToMoney != null ? !onlinePointToMoney.equals(that.onlinePointToMoney) : that.onlinePointToMoney != null)
            return false;
        if (offlinePointToMoney != null ? !offlinePointToMoney.equals(that.offlinePointToMoney) : that.offlinePointToMoney != null)
            return false;
        if (canOtherActivity != null ? !canOtherActivity.equals(that.canOtherActivity) : that.canOtherActivity != null)
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
        result = 31 * result + (onlinePointToMoney != null ? onlinePointToMoney.hashCode() : 0);
        result = 31 * result + (offlinePointToMoney != null ? offlinePointToMoney.hashCode() : 0);
        result = 31 * result + (canOtherActivity != null ? canOtherActivity.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (statusTime != null ? statusTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        return result;
    }
}
