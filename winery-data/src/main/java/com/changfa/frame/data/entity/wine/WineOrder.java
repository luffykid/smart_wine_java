package com.changfa.frame.data.entity.wine;

import lombok.Builder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "wine_order", schema = "winery", catalog = "")
public class WineOrder {
    private Integer id;
    private Integer wineryId;
    private Integer userId;
    private String orderNo;
    private Integer wineQuantity;
    private BigDecimal storageAmount;
    private BigDecimal donationAmount;
    private String type;
    private String descri;
    private Integer wineId;
    private BigDecimal totalPrice;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "wine_quantity")
    public Integer getWineQuantity() {
        return wineQuantity;
    }

    public void setWineQuantity(Integer wineQuantity) {
        this.wineQuantity = wineQuantity;
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
    @Column(name = "descri")
    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
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
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    @Basic
    @Column(name = "storage_amount")
    public BigDecimal getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(BigDecimal storageAmount) {
        this.storageAmount = storageAmount;
    }

    @Basic
    @Column(name = "donation_amount")
    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WineOrder wineOrder = (WineOrder) o;
        return id == wineOrder.id &&
                wineryId == wineOrder.wineryId &&
                userId == wineOrder.userId &&
                Objects.equals(orderNo, wineOrder.orderNo) &&
                Objects.equals(wineQuantity, wineOrder.wineQuantity) &&
                Objects.equals(storageAmount, wineOrder.storageAmount) &&
                Objects.equals(donationAmount, wineOrder.donationAmount) &&
                Objects.equals(type, wineOrder.type) &&
                Objects.equals(descri, wineOrder.descri) &&
                Objects.equals(wineId, wineOrder.wineId) &&
                Objects.equals(totalPrice, wineOrder.totalPrice) &&
                Objects.equals(createTime, wineOrder.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wineryId, userId, orderNo, wineQuantity,storageAmount,donationAmount, type, descri, wineId, totalPrice, createTime);
    }
}
