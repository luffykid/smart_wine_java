package com.changfa.frame.data.entity.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/9.
 */
@Entity
@Table(name = "order_settle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OrderSettle {
    private Integer id;
    private Integer orderId;
    private Integer userId;
    private Integer useVoucherId;
    private BigDecimal useVoucherPrice;
    private BigDecimal usePointPrice;
    private BigDecimal finalTotalPrice;
    private Integer usePoint;
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
    @Column(name = "use_voucher_price")
    public BigDecimal getUseVoucherPrice() {
        return useVoucherPrice;
    }

    public void setUseVoucherPrice(BigDecimal useVoucherPrice) {
        this.useVoucherPrice = useVoucherPrice;
    }
    @Basic
    @Column(name = "use_point_price")
    public BigDecimal getUsePointPrice() {
        return usePointPrice;
    }

    public void setUsePointPrice(BigDecimal usePointPrice) {
        this.usePointPrice = usePointPrice;
    }
    @Basic
    @Column(name = "final_total_price")
    public BigDecimal getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(BigDecimal finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "use_voucher_id")
    public Integer getUseVoucherId() {
        return useVoucherId;
    }

    public void setUseVoucherId(Integer useVoucherId) {
        this.useVoucherId = useVoucherId;
    }

    @Basic
    @Column(name = "use_point")
    public Integer getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(Integer usePoint) {
        this.usePoint = usePoint;
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

        OrderSettle that = (OrderSettle) o;

        if (id != that.id) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (useVoucherId != null ? !useVoucherId.equals(that.useVoucherId) : that.useVoucherId != null) return false;
        if (usePoint != null ? !usePoint.equals(that.usePoint) : that.usePoint != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (useVoucherId != null ? useVoucherId.hashCode() : 0);
        result = 31 * result + (usePoint != null ? usePoint.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
