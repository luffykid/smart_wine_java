package com.changfa.frame.data.entity.offline;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "offline_order_price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OfflineOrderPrice {
    private Integer id;
    private Integer offlineOrderId;
    private BigDecimal origPrice;
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
    @Column(name = "offline_order_id")
    public Integer getOfflineOrderId() {
        return offlineOrderId;
    }

    public void setOfflineOrderId(Integer offlineOrderId) {
        this.offlineOrderId = offlineOrderId;
    }

    @Basic
    @Column(name = "orig_price")
    public BigDecimal getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(BigDecimal origPrice) {
        this.origPrice = origPrice;
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
        OfflineOrderPrice that = (OfflineOrderPrice) o;
        return id == that.id &&
                offlineOrderId == that.offlineOrderId &&
                Objects.equals(origPrice, that.origPrice) &&
                Objects.equals(finalPrice, that.finalPrice) &&
                Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, offlineOrderId, origPrice, finalPrice, totalPrice);
    }
}
