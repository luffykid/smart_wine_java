package com.changfa.frame.data.dto.saas;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/18 0018.
 */
public class DepositRuleItemDTO implements Serializable {
    private String depositMoney;
    private String presentMoney;
    private String presentVoucherId;
    private String mes;

    public String getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(String depositMoney) {
        this.depositMoney = depositMoney;
    }

    public String getPresentMoney() {
        return presentMoney;
    }

    public void setPresentMoney(String presentMoney) {
        this.presentMoney = presentMoney;
    }

    public String getPresentVoucherId() {
        return presentVoucherId;
    }

    public void setPresentVoucherId(String presentVoucherId) {
        this.presentVoucherId = presentVoucherId;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
