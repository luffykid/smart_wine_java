package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.math.BigDecimal;

public class StatisticDTO implements Serializable {
    private String time;
    private BigDecimal deposit;
    private BigDecimal consumption;
    private Integer count;
    private Integer depositCount;
    private Integer consumptionCount;
    private Integer LoginCount;
    private Integer giftCount;
    private Integer sendVoucherCount;
    private Integer useVoucherConunt;
    private Integer ineffectiveCount;
    private String name;
    private BigDecimal money;
    private String icon;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDepositCount() {
        return depositCount;
    }

    public void setDepositCount(Integer depositCount) {
        this.depositCount = depositCount;
    }

    public Integer getConsumptionCount() {
        return consumptionCount;
    }

    public void setConsumptionCount(Integer consumptionCount) {
        this.consumptionCount = consumptionCount;
    }

    public Integer getLoginCount() {
        return LoginCount;
    }

    public void setLoginCount(Integer loginCount) {
        LoginCount = loginCount;
    }

    public Integer getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(Integer giftCount) {
        this.giftCount = giftCount;
    }

    public Integer getSendVoucherCount() {
        return sendVoucherCount;
    }

    public void setSendVoucherCount(Integer sendVoucherCount) {
        this.sendVoucherCount = sendVoucherCount;
    }

    public Integer getUseVoucherConunt() {
        return useVoucherConunt;
    }

    public void setUseVoucherConunt(Integer useVoucherConunt) {
        this.useVoucherConunt = useVoucherConunt;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public Integer getIneffectiveCount() {
        return ineffectiveCount;
    }

    public void setIneffectiveCount(Integer ineffectiveCount) {
        this.ineffectiveCount = ineffectiveCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public StatisticDTO(String time, BigDecimal deposit) {
        this.time = time;
        this.deposit = deposit;
    }

    public StatisticDTO() {
    }
}
