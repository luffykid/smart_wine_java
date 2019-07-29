package com.changfa.frame.data.entity.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "order_pay")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OrderPay {
    private Integer id;
    private Integer orderId;
    private String orderNo;
    private String orderType;
    private Integer userId;
    private Date payTime;
    private String payType;
    private BigDecimal totalPrice;
    private String payStatus;
    private Date payStatusTime;
    private String notifyStatus;
    private Date notifyTime;
    private String platformOrderNo;
    private String platformAccount;
    private String subject;
    private String remark;

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
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "pay_time")
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Basic
    @Column(name = "pay_type")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "pay_status")
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Basic
    @Column(name = "pay_status_time")
    public Date getPayStatusTime() {
        return payStatusTime;
    }

    public void setPayStatusTime(Date payStatusTime) {
        this.payStatusTime = payStatusTime;
    }

    @Basic
    @Column(name = "notify_status")
    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    @Basic
    @Column(name = "notify_time")
    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    @Basic
    @Column(name = "platform_order_no")
    public String getPlatformOrderNo() {
        return platformOrderNo;
    }

    public void setPlatformOrderNo(String platformOrderNo) {
        this.platformOrderNo = platformOrderNo;
    }

    @Basic
    @Column(name = "platform_account")
    public String getPlatformAccount() {
        return platformAccount;
    }

    public void setPlatformAccount(String platformAccount) {
        this.platformAccount = platformAccount;
    }

    @Basic
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPay orderPay = (OrderPay) o;
        return id == orderPay.id &&
                orderId == orderPay.orderId &&
                userId == orderPay.userId &&
                Objects.equals(orderNo, orderPay.orderNo) &&
                Objects.equals(orderType, orderPay.orderType) &&
                Objects.equals(payTime, orderPay.payTime) &&
                Objects.equals(payType, orderPay.payType) &&
                Objects.equals(totalPrice, orderPay.totalPrice) &&
                Objects.equals(payStatus, orderPay.payStatus) &&
                Objects.equals(payStatusTime, orderPay.payStatusTime) &&
                Objects.equals(notifyStatus, orderPay.notifyStatus) &&
                Objects.equals(notifyTime, orderPay.notifyTime) &&
                Objects.equals(platformOrderNo, orderPay.platformOrderNo) &&
                Objects.equals(platformAccount, orderPay.platformAccount) &&
                Objects.equals(subject, orderPay.subject) &&
                Objects.equals(remark, orderPay.remark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderId, orderNo, orderType, userId, payTime, payType, totalPrice, payStatus, payStatusTime, notifyStatus, notifyTime, platformOrderNo, platformAccount, subject, remark);
    }
}
