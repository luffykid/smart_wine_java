package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/11/19.
 */
public class OrdersDTO {
    private Integer orderId;
    private String orderNo;
    private Date orderTime;//下单时间
    private String expressMethod;//配送方式
    private List<NewProdListDTO> newProdLists;
    private String status;
    private String statusName;
    private Integer userAddressId;
    private String userName;
    private String phone;
    private String address;
    private String expressPrice;//运费
    private String totalPrice;//合计
    private String totalProdPrice;//商品总额
    private String ghsj;//供货商家
    private String voucherName;//优惠券
    private Integer totalPoint;//商品积分
    private StringBuffer time;
    private BigDecimal balance;

    public String getTotalProdPrice() {
        return totalProdPrice;
    }

    public void setTotalProdPrice(String totalProdPrice) {
        this.totalProdPrice = totalProdPrice;
    }

    public String getGhsj() {
        return ghsj;
    }

    public void setGhsj(String ghsj) {
        this.ghsj = ghsj;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getExpressMethod() {
        return expressMethod;
    }

    public void setExpressMethod(String expressMethod) {
        this.expressMethod = expressMethod;
    }

    public List<NewProdListDTO> getNewProdLists() {
        return newProdLists;
    }

    public void setNewProdLists(List<NewProdListDTO> newProdLists) {
        this.newProdLists = newProdLists;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public StringBuffer getTime() {
        return time;
    }

    public void setTime(StringBuffer time) {
        this.time = time;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
