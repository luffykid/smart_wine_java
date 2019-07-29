package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.math.BigDecimal;


public class DepositOrderDetailDTO implements Serializable {

    private int depositOrderId;
    private String time;
    private BigDecimal totalPrice;
    private BigDecimal payMoney;
    private BigDecimal rewardMoney;

    public int getDepositOrderId() {
        return depositOrderId;
    }

    public void setDepositOrderId(int depositOrderId) {
        this.depositOrderId = depositOrderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(BigDecimal rewardMoney) {
        this.rewardMoney = rewardMoney;
    }


}
