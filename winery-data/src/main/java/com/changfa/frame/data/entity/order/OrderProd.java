package com.changfa.frame.data.entity.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/11/9.
 */
@Entity
@Table(name = "order_prod")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OrderProd {
    private Integer id;
    private Integer wineryId;
    private Integer orderId;
    private Integer prodId;
    private Integer quantity;
    private Integer prodSpecId;

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
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "prod_spec_id")
    public Integer getProdSpecId() {
        return prodSpecId;
    }

    public void setProdSpecId(Integer prodSpecId) {
        this.prodSpecId = prodSpecId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProd that = (OrderProd) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (prodId != that.prodId) return false;
        if (quantity != that.quantity) return false;
        if (wineryId != null ? !wineryId.equals(that.wineryId) : that.wineryId != null) return false;
        if (prodSpecId != null ? !prodSpecId.equals(that.prodSpecId) : that.prodSpecId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (wineryId != null ? wineryId.hashCode() : 0);
        result = 31 * result + orderId;
        result = 31 * result + prodId;
        result = 31 * result + quantity;
        result = 31 * result + (prodSpecId != null ? prodSpecId.hashCode() : 0);
        return result;
    }
}