package com.changfa.frame.data.entity.market;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@Entity
@Table(name = "market_activity_spec_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class MarketActivitySpecDetail {
    private Integer id;
    private Integer marketActivityId;
    private BigDecimal presentMoney;
    private Integer presentVoucherId;
    private Integer presentVoucherQuantity;
    private BigDecimal depositMoney;

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
    @Column(name = "market_activity_id")
    public Integer getMarketActivityId() {
        return marketActivityId;
    }

    public void setMarketActivityId(Integer marketActivityId) {
        this.marketActivityId = marketActivityId;
    }

    @Basic
    @Column(name = "present_money")
    public BigDecimal getPresentMoney() {
        return presentMoney;
    }

    public void setPresentMoney(BigDecimal presentMoney) {
        this.presentMoney = presentMoney;
    }

    @Basic
    @Column(name = "present_voucher_id")
    public Integer getPresentVoucherId() {
        return presentVoucherId;
    }

    public void setPresentVoucherId(Integer presentVoucherId) {
        this.presentVoucherId = presentVoucherId;
    }

    @Basic
    @Column(name = "present_voucher_quantity")
    public Integer getPresentVoucherQuantity() {
        return presentVoucherQuantity;
    }

    public void setPresentVoucherQuantity(Integer presentVoucherQuantity) {
        this.presentVoucherQuantity = presentVoucherQuantity;
    }

    @Basic
    @Column(name = "deposit_money")
    public BigDecimal getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(BigDecimal depositMoney) {
        this.depositMoney = depositMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketActivitySpecDetail that = (MarketActivitySpecDetail) o;

        if (id != that.id) return false;
        if (marketActivityId != null ? !marketActivityId.equals(that.marketActivityId) : that.marketActivityId != null)
            return false;
        if (presentMoney != null ? !presentMoney.equals(that.presentMoney) : that.presentMoney != null) return false;
        if (presentVoucherId != null ? !presentVoucherId.equals(that.presentVoucherId) : that.presentVoucherId != null)
            return false;
        if (presentVoucherQuantity != null ? !presentVoucherQuantity.equals(that.presentVoucherQuantity) : that.presentVoucherQuantity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (marketActivityId != null ? marketActivityId.hashCode() : 0);
        result = 31 * result + (presentMoney != null ? presentMoney.hashCode() : 0);
        result = 31 * result + (presentVoucherId != null ? presentVoucherId.hashCode() : 0);
        result = 31 * result + (presentVoucherQuantity != null ? presentVoucherQuantity.hashCode() : 0);
        return result;
    }
}
