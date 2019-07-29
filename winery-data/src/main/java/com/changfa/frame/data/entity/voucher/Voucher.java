package com.changfa.frame.data.entity.voucher;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Administrator on 2018/10/12 0012.
 */
@Entity
@Table(name = "voucher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Voucher {
    private int id;
    private int wineryId;
    private String name;
    private String type;
    private BigDecimal money;
    private BigDecimal discount;
    private Integer exchangeProdId;
    private String exchangeProdName;
    private String enableType;
    private BigDecimal enableMoeny;
    private Integer quantityLimit;
    private Integer enableDay;
    private Integer usefulTime;
    private Date effectiveTime;
    private Date ineffectiveTime;
    private String scope;
    private String canPresent;
    private Integer createUserId;
    private String status;
    private Date statusTime;
    private Date createTime;

    @Transient
    public String getExchangeProdName() {
        return exchangeProdName;
    }

    public void setExchangeProdName(String exchangeProdName) {
        this.exchangeProdName = exchangeProdName;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "winery_id")
    public int getWineryId() {
        return wineryId;
    }

    public void setWineryId(int wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "money")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Basic
    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "exchange_prod_id")
    public Integer getExchangeProdId() {
        return exchangeProdId;
    }

    public void setExchangeProdId(Integer exchangeProdId) {
        this.exchangeProdId = exchangeProdId;
    }

    @Basic
    @Column(name = "enable_type")
    public String getEnableType() {
        return enableType;
    }

    public void setEnableType(String enableType) {
        this.enableType = enableType;
    }

    @Basic
    @Column(name = "enable_moeny")
    public BigDecimal getEnableMoeny() {
        return enableMoeny;
    }

    public void setEnableMoeny(BigDecimal enableMoeny) {
        this.enableMoeny = enableMoeny;
    }

    @Basic
    @Column(name = "quantity_limit")
    public Integer getQuantityLimit() {
        return quantityLimit;
    }

    public void setQuantityLimit(Integer quantityLimit) {
        this.quantityLimit = quantityLimit;
    }

    @Basic
    @Column(name = "enable_day")
    public Integer getEnableDay() {
        return enableDay;
    }

    public void setEnableDay(Integer enableDay) {
        this.enableDay = enableDay;
    }

    @Basic
    @Column(name = "useful_time")
    public Integer getUsefulTime() {
        return usefulTime;
    }

    public void setUsefulTime(Integer usefulTime) {
        this.usefulTime = usefulTime;
    }

    @Basic
    @Column(name = "effective_time")
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    @Basic
    @Column(name = "ineffective_time")
    public Date getIneffectiveTime() {
        return ineffectiveTime;
    }

    public void setIneffectiveTime(Date ineffectiveTime) {
        this.ineffectiveTime = ineffectiveTime;
    }

    @Basic
    @Column(name = "scope")
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Basic
    @Column(name = "can_present")
    public String getCanPresent() {
        return canPresent;
    }

    public void setCanPresent(String canPresent) {
        this.canPresent = canPresent;
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
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "status_time")
    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Voucher that = (Voucher) o;

        if (id != that.id) return false;
        if (wineryId != that.wineryId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
        if (exchangeProdId != null ? !exchangeProdId.equals(that.exchangeProdId) : that.exchangeProdId != null)
            return false;
        if (enableType != null ? !enableType.equals(that.enableType) : that.enableType != null) return false;
        if (enableMoeny != null ? !enableMoeny.equals(that.enableMoeny) : that.enableMoeny != null) return false;
        if (quantityLimit != null ? !quantityLimit.equals(that.quantityLimit) : that.quantityLimit != null)
            return false;
        if (enableDay != null ? !enableDay.equals(that.enableDay) : that.enableDay != null) return false;
        if (usefulTime != null ? !usefulTime.equals(that.usefulTime) : that.usefulTime != null) return false;
        if (effectiveTime != null ? !effectiveTime.equals(that.effectiveTime) : that.effectiveTime != null)
            return false;
        if (ineffectiveTime != null ? !ineffectiveTime.equals(that.ineffectiveTime) : that.ineffectiveTime != null)
            return false;
        if (scope != null ? !scope.equals(that.scope) : that.scope != null) return false;
        if (canPresent != null ? !canPresent.equals(that.canPresent) : that.canPresent != null) return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId) : that.createUserId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (statusTime != null ? !statusTime.equals(that.statusTime) : that.statusTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + wineryId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (exchangeProdId != null ? exchangeProdId.hashCode() : 0);
        result = 31 * result + (enableType != null ? enableType.hashCode() : 0);
        result = 31 * result + (enableMoeny != null ? enableMoeny.hashCode() : 0);
        result = 31 * result + (quantityLimit != null ? quantityLimit.hashCode() : 0);
        result = 31 * result + (enableDay != null ? enableDay.hashCode() : 0);
        result = 31 * result + (usefulTime != null ? usefulTime.hashCode() : 0);
        result = 31 * result + (effectiveTime != null ? effectiveTime.hashCode() : 0);
        result = 31 * result + (ineffectiveTime != null ? ineffectiveTime.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (canPresent != null ? canPresent.hashCode() : 0);
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (statusTime != null ? statusTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
