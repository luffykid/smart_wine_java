package com.changfa.frame.data.entity.deposit;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "deposit_rule_spec_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class DepositRuleSpecDetail {
    private Integer id;
    private Integer depositRuleId;
    private BigDecimal depositMoney;
    private BigDecimal presentMoney;
    private Integer presentVoucherId;

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
    @Column(name = "deposit_rule_id")
    public Integer getDepositRuleId() {
        return depositRuleId;
    }

    public void setDepositRuleId(Integer depositRuleId) {
        this.depositRuleId = depositRuleId;
    }

    @Basic
    @Column(name = "deposit_money")
    public BigDecimal getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(BigDecimal depositMoney) {
        this.depositMoney = depositMoney;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositRuleSpecDetail that = (DepositRuleSpecDetail) o;
        return id == that.id &&
                depositRuleId == that.depositRuleId &&
                Objects.equals(depositMoney, that.depositMoney) &&
                Objects.equals(presentMoney, that.presentMoney) &&
                Objects.equals(presentVoucherId, that.presentVoucherId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, depositRuleId, depositMoney, presentMoney, presentVoucherId);
    }
}
