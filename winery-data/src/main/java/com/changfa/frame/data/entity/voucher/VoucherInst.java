package com.changfa.frame.data.entity.voucher;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "voucher_inst")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class VoucherInst {
    private Integer id;
    private Integer wineryId;
    private String name;
    private Integer voucherId;
    private String voucharNo;
    private String type;
    private String scope;
    private String canPresent;
    private BigDecimal money;
    private BigDecimal discount;
    private Integer exchangeProdId;
    private String enableType;
    private BigDecimal enableMoney;
    private Date effectiveTime;
    private Date ineffectiveTime;
    private String status;
    private Integer comeActivityId;
    private String comeActivityType;
    private String orderType;
    private Integer orderId;
    private Date statusTime;
    private Date createTime;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "voucher_id")
    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    @Basic
    @Column(name = "vouchar_no")
    public String getVoucharNo() {
        return voucharNo;
    }

    public void setVoucharNo(String voucharNo) {
        this.voucharNo = voucharNo;
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
    @Column(name = "enable_money")
    public BigDecimal getEnableMoney() {
        return enableMoney;
    }

    public void setEnableMoney(BigDecimal enableMoney) {
        this.enableMoney = enableMoney;
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
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "come_activity_id")
    public Integer getComeActivityId() {
        return comeActivityId;
    }

    public void setComeActivityId(Integer comeActivityId) {
        this.comeActivityId = comeActivityId;
    }

    @Basic
    @Column(name = "come_activiy_type")
    public String getComActivityType() {
        return comeActivityType;
    }

    public void setComActivityType(String comActivityType) {
        this.comeActivityType = comActivityType;
    }

    @Basic
    @Column(name = "order_type")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
        VoucherInst that = (VoucherInst) o;
        return id == that.id &&
                wineryId == that.wineryId &&
                voucherId == that.voucherId &&
                voucharNo == that.voucharNo &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(scope, that.scope) &&
                Objects.equals(canPresent, that.canPresent) &&
                Objects.equals(money, that.money) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(exchangeProdId, that.exchangeProdId) &&
                Objects.equals(enableType, that.enableType) &&
                Objects.equals(enableMoney, that.enableMoney) &&
                Objects.equals(effectiveTime, that.effectiveTime) &&
                Objects.equals(ineffectiveTime, that.ineffectiveTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(statusTime, that.statusTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, wineryId, name, voucherId, voucharNo, type, scope, canPresent, money, discount, exchangeProdId, enableType, enableMoney, effectiveTime, ineffectiveTime, status, statusTime, createTime);
    }

    public VoucherInst(Integer wineryId, String name, Integer voucherId, String voucharNo, String type, String scope, String canPresent, BigDecimal money, BigDecimal discount, Integer exchangeProdId, String enableType, BigDecimal enableMoney, Date effectiveTime, Date ineffectiveTime, String status, Date statusTime, Date createTime) {
        this.wineryId = wineryId;
        this.name = name;
        this.voucherId = voucherId;
        this.voucharNo = voucharNo;
        this.type = type;
        this.scope = scope;
        this.canPresent = canPresent;
        this.money = money;
        this.discount = discount;
        this.exchangeProdId = exchangeProdId;
        this.enableType = enableType;
        this.enableMoney = enableMoney;
        this.effectiveTime = effectiveTime;
        this.ineffectiveTime = ineffectiveTime;
        this.status = status;
        this.statusTime = statusTime;
        this.createTime = createTime;
    }

    public VoucherInst() {
    }
}
