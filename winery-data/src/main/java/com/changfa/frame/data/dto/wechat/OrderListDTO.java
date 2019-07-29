package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/11/15.
 */
public class OrderListDTO {
    private Integer orderId;
    private String orderNo;
    private String statusName;
    private String status;
    private List<NewProdListDTO> prodItem;
    private Integer prodNum;
    private String totalPrice;
    private String prodPrice;
    private BigDecimal balance;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<NewProdListDTO> getProdItem() {
        return prodItem;
    }

    public void setProdItem(List<NewProdListDTO> prodItem) {
        this.prodItem = prodItem;
    }

    public Integer getProdNum() {
        return prodNum;
    }

    public void setProdNum(Integer prodNum) {
        this.prodNum = prodNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
