package com.changfa.frame.data.entity.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/11/9.
 */
@Entity
@Table(name = "order_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OrderPrice {
    private Integer id;
    private Integer wineryId;
    private Integer orderId;
    private BigDecimal prodOrigPrice;
    private BigDecimal prodFinalPrice;
    private BigDecimal carriageExpense;
    private BigDecimal totalPrice;

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
    @Column(name = "prod_orig_price")
    public BigDecimal getProdOrigPrice() {
        return prodOrigPrice;
    }

    public void setProdOrigPrice(BigDecimal prodOrigPrice) {
        this.prodOrigPrice = prodOrigPrice;
    }

    @Basic
    @Column(name = "prod_final_price")
    public BigDecimal getProdFinalPrice() {
        return prodFinalPrice;
    }

    public void setProdFinalPrice(BigDecimal prodFinalPrice) {
        this.prodFinalPrice = prodFinalPrice;
    }

    @Basic
    @Column(name = "carriage_expense")
    public BigDecimal getCarriageExpense() {
        return carriageExpense;
    }

    public void setCarriageExpense(BigDecimal carriageExpense) {
        this.carriageExpense = carriageExpense;
    }

    @Basic
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPrice that = (OrderPrice) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (wineryId != null ? !wineryId.equals(that.wineryId) : that.wineryId != null) return false;
        if (prodOrigPrice != null ? !prodOrigPrice.equals(that.prodOrigPrice) : that.prodOrigPrice != null)
            return false;
        if (prodFinalPrice != null ? !prodFinalPrice.equals(that.prodFinalPrice) : that.prodFinalPrice != null)
            return false;
        if (carriageExpense != null ? !carriageExpense.equals(that.carriageExpense) : that.carriageExpense != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (wineryId != null ? wineryId.hashCode() : 0);
        result = 31 * result + orderId;
        result = 31 * result + (prodOrigPrice != null ? prodOrigPrice.hashCode() : 0);
        result = 31 * result + (prodFinalPrice != null ? prodFinalPrice.hashCode() : 0);
        result = 31 * result + (carriageExpense != null ? carriageExpense.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }
}
