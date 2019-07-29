package com.changfa.frame.data.entity.activity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "activity_order_price_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ActivityOrderPriceItem {
    private Integer id;
    private Integer activityOrderId;
    private Integer activityOrderPriceId;
    private Integer activityId;
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
    @Column(name = "activity_order_id")
    public Integer getActivityOrderId() {
        return activityOrderId;
    }

    public void setActivityOrderId(Integer activityOrderId) {
        this.activityOrderId = activityOrderId;
    }

    @Basic
    @Column(name = "activity_order_price_id")
    public Integer getActivityOrderPriceId() {
        return activityOrderPriceId;
    }

    public void setActivityOrderPriceId(Integer activityOrderPriceId) {
        this.activityOrderPriceId = activityOrderPriceId;
    }

    @Basic
    @Column(name = "activity_id")
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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
        ActivityOrderPriceItem that = (ActivityOrderPriceItem) o;
        return id == that.id &&
                activityOrderId == that.activityOrderId &&
                activityOrderPriceId == that.activityOrderPriceId &&
                activityId == that.activityId &&
                Objects.equals(origUnitPrice, that.origUnitPrice) &&
                Objects.equals(origTotalPrice, that.origTotalPrice) &&
                Objects.equals(finalUnitPrice, that.finalUnitPrice) &&
                Objects.equals(finalTotalPrice, that.finalTotalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityOrderId, activityOrderPriceId, activityId, origUnitPrice, origTotalPrice, finalUnitPrice, finalTotalPrice);
    }
}
