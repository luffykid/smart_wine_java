package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketDTO implements Serializable {


    private String activityName;
    private String activityType;
    private String time;
    private Integer sendCount;
    private Integer useCount;
    private String useChance;
    private BigDecimal discountMoney;
    private BigDecimal consumption;
    private BigDecimal userGetMoney;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public String getUseChance() {
        return useChance;
    }

    public void setUseChance(String useChance) {
        this.useChance = useChance;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public BigDecimal getUserGetMoney() {
        return userGetMoney;
    }

    public void setUserGetMoney(BigDecimal userGetMoney) {
        this.userGetMoney = userGetMoney;
    }
}
