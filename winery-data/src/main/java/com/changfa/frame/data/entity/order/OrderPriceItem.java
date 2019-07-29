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
@Table(name = "order_price_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OrderPriceItem {
    private Integer id;
    private Integer wineryId;
    private Integer orderId;
    private Integer orderPriceId;
    private Integer prodId;
    private Integer prodSpecId;
    private Integer quantity;
    private BigDecimal origUnitPrice;
    private BigDecimal origTotalPrice;
    private BigDecimal finalUnitPrice;
    private BigDecimal finalTotalPrice;

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
    @Column(name = "order_price_id")
    public Integer getOrderPriceId() {
        return orderPriceId;
    }

    public void setOrderPriceId(Integer orderPriceId) {
        this.orderPriceId = orderPriceId;
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
    @Column(name = "prod_spec_id")
    public Integer getProdSpecId() {
        return prodSpecId;
    }

    public void setProdSpecId(Integer prodSpecId) {
        this.prodSpecId = prodSpecId;
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
    @Column(name = "orig_unit_price")
    public BigDecimal getOrigUnitPrice() {
        return origUnitPrice;
    }

    public void setOrigUnitPrice(BigDecimal origUnitPrice) {
        this.origUnitPrice = origUnitPrice;
    }

    @Basic
    @Column(name = "orig_total_price")
    public BigDecimal getOrigTotalPrice() {
        return origTotalPrice;
    }

    public void setOrigTotalPrice(BigDecimal origTotalPrice) {
        this.origTotalPrice = origTotalPrice;
    }

    @Basic
    @Column(name = "final_unit_price")
    public BigDecimal getFinalUnitPrice() {
        return finalUnitPrice;
    }

    public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }

    @Basic
    @Column(name = "final_total_price")
    public BigDecimal getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(BigDecimal finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPriceItem that = (OrderPriceItem) o;

        if (id != that.id) return false;
        if (wineryId != null ? !wineryId.equals(that.wineryId) : that.wineryId != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderPriceId != null ? !orderPriceId.equals(that.orderPriceId) : that.orderPriceId != null) return false;
        if (prodId != null ? !prodId.equals(that.prodId) : that.prodId != null) return false;
        if (prodSpecId != null ? !prodSpecId.equals(that.prodSpecId) : that.prodSpecId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (origUnitPrice != null ? !origUnitPrice.equals(that.origUnitPrice) : that.origUnitPrice != null)
            return false;
        if (origTotalPrice != null ? !origTotalPrice.equals(that.origTotalPrice) : that.origTotalPrice != null)
            return false;
        if (finalUnitPrice != null ? !finalUnitPrice.equals(that.finalUnitPrice) : that.finalUnitPrice != null)
            return false;
        if (finalTotalPrice != null ? !finalTotalPrice.equals(that.finalTotalPrice) : that.finalTotalPrice != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (wineryId != null ? wineryId.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderPriceId != null ? orderPriceId.hashCode() : 0);
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (prodSpecId != null ? prodSpecId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (origUnitPrice != null ? origUnitPrice.hashCode() : 0);
        result = 31 * result + (origTotalPrice != null ? origTotalPrice.hashCode() : 0);
        result = 31 * result + (finalUnitPrice != null ? finalUnitPrice.hashCode() : 0);
        result = 31 * result + (finalTotalPrice != null ? finalTotalPrice.hashCode() : 0);
        return result;
    }
}
