package com.changfa.frame.data.entity.wine;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_wine_detail", schema = "winery", catalog = "")
public class UserWineDetail {
    private Integer id;
    private Integer userId;
    private Integer wineryId;
    private String wineType;
    private String action;
    private Integer wine;
    private Integer wineId;
    private Integer lastWine;
    private String orderNo;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "wine_type")
    public String getWineType() {
        return wineType;
    }

    public void setWineType(String wineType) {
        this.wineType = wineType;
    }

    @Basic
    @Column(name = "action")
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "wine")
    public Integer getWine() {
        return wine;
    }

    public void setWine(Integer wine) {
        this.wine = wine;
    }

    @Basic
    @Column(name = "wine_id")
    public Integer getWineId() {
        return wineId;
    }

    public void setWineId(Integer wineId) {
        this.wineId = wineId;
    }

    @Basic
    @Column(name = "last_wine")
    public Integer getLastWine() {
        return lastWine;
    }

    public void setLastWine(Integer lastWine) {
        this.lastWine = lastWine;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
        UserWineDetail that = (UserWineDetail) o;
        return id == that.id &&
                userId == that.userId &&
                wineryId == that.wineryId &&
                Objects.equals(wineType, that.wineType) &&
                Objects.equals(action, that.action) &&
                Objects.equals(wine, that.wine) &&
                Objects.equals(lastWine, that.lastWine) &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, wineryId, wineType, action, wine, lastWine, orderNo, createTime);
    }
}
