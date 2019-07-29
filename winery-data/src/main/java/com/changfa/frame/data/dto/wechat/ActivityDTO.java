package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActivityDTO implements Serializable {

    private int activityId;
    private String name;
    private String address;
    private String beginTime;
    private String endTime;
    private Date ticketBeginTime;
    private Date ticketEndTime;
    private String status;
    private BigDecimal price;
    private String logo;
    private String content;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String contacts;
    private String phone;
    private Integer voucherInstId;
    private BigDecimal minus;
    private String orderNo;
    private String balancePayStatus;
    private BigDecimal userBalance;
    private String isJoin;
    


    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getVoucherInstId() {
        return voucherInstId;
    }

    public void setVoucherInstId(Integer voucherInstId) {
        this.voucherInstId = voucherInstId;
    }

    public Date getTicketBeginTime() {
        return ticketBeginTime;
    }

    public void setTicketBeginTime(Date ticketBeginTime) {
        this.ticketBeginTime = ticketBeginTime;
    }

    public BigDecimal getMinus() {
        return minus;
    }

    public void setMinus(BigDecimal minus) {
        this.minus = minus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBalancePayStatus() {
        return balancePayStatus;
    }

    public void setBalancePayStatus(String balancePayStatus) {
        this.balancePayStatus = balancePayStatus;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public Date getTicketEndTime() {
        return ticketEndTime;
    }

    public void setTicketEndTime(Date ticketEndTime) {
        this.ticketEndTime = ticketEndTime;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }
}
