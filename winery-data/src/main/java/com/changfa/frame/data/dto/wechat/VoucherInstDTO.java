package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.math.BigDecimal;

public class VoucherInstDTO implements Serializable {

    private Integer voucherInstId;
    private String name;
    private String rule;
    private String scope;
    private String usefulTime;
    private BigDecimal money;
    private String disCount;
    private String status;
    private String type;
    private String gift;
    private Integer point;
    private String sendType;
    private String prodPrice;
    private String canPresent;

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUsefulTime() {
        return usefulTime;
    }

    public void setUsefulTime(String usefulTime) {
        this.usefulTime = usefulTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisCount() {
        return disCount;
    }

    public void setDisCount(String disCount) {
        this.disCount = disCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public void setVoucherInstId(Integer voucherInstId) {
        this.voucherInstId = voucherInstId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getVoucherInstId() {
        return voucherInstId;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getCanPresent() {
        return canPresent;
    }

    public void setCanPresent(String canPresent) {
        this.canPresent = canPresent;
    }
}
