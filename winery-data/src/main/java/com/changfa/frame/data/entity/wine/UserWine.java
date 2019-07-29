package com.changfa.frame.data.entity.wine;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_wine", schema = "winery", catalog = "")
public class UserWine {
    private Integer id;
    private Integer wineId;
    private Integer userId;
    private Integer quantity;
    private Date updateTime;

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
    @Column(name = "wine_id")
    public Integer getWineId() {
        return wineId;
    }

    public void setWineId(Integer wineId) {
        this.wineId = wineId;
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
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWine userWine = (UserWine) o;
        return id == userWine.id &&
                Objects.equals(wineId, userWine.wineId) &&
                Objects.equals(quantity, userWine.quantity) &&
                Objects.equals(updateTime, userWine.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, wineId, quantity, updateTime);
    }
}
