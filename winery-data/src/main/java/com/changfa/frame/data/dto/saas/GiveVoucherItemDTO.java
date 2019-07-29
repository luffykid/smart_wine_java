package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public class GiveVoucherItemDTO implements Serializable {
    private Integer presentVoucherId;
    private Integer presentVoucherQuantity;
    private String value;
    private String depositMoney;
    private String presentMoney;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    public Integer getPresentVoucherId() {
        return presentVoucherId;
    }

    public void setPresentVoucherId(Integer presentVoucherId) {
        this.presentVoucherId = presentVoucherId;
    }

    public Integer getPresentVoucherQuantity() {
        return presentVoucherQuantity;
    }

    public void setPresentVoucherQuantity(Integer presentVoucherQuantity) {
        this.presentVoucherQuantity = presentVoucherQuantity;
    }
}
