package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.math.BigDecimal;

public class DepositRuleDTO implements Serializable {

    private BigDecimal money;
    private BigDecimal sendMoney;
    private String voucherName;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(BigDecimal sendMoney) {
        this.sendMoney = sendMoney;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }
}
