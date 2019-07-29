package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserDepositDetailDTO implements Serializable {

    private Integer orderId;
    private String action;
    private Date createTime;
    private BigDecimal money;
    private BigDecimal rewardMoney; // 赠送金额

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(BigDecimal rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
}
