package com.changfa.frame.data.entity.wine;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "wine_order_price", schema = "winery", catalog = "")
public class WineOrderPrice {
    private Integer id;
    private Integer wineOrderId;
    private BigDecimal wineOrigPrice;
    private BigDecimal finalPrice;
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
    @Column(name = "wine_order_id")
    public Integer getWineOrderId() {
        return wineOrderId;
    }

    public void setWineOrderId(Integer wineOrderId) {
        this.wineOrderId = wineOrderId;
    }

    @Basic
    @Column(name = "wine_orig_price")
    public BigDecimal getWineOrigPrice() {
        return wineOrigPrice;
    }

    public void setWineOrigPrice(BigDecimal wineOrigPrice) {
        this.wineOrigPrice = wineOrigPrice;
    }

    @Basic
    @Column(name = "final_price")
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
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
        WineOrderPrice that = (WineOrderPrice) o;
        return id == that.id &&
                Objects.equals(wineOrderId, that.wineOrderId) &&
                Objects.equals(wineOrigPrice, that.wineOrigPrice) &&
                Objects.equals(finalPrice, that.finalPrice) &&
                Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, wineOrderId, wineOrigPrice, finalPrice, totalPrice);
    }
}
