package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

public class OfflineOrderDetailDTO {
    private Integer orderId;
    private String payType;
    private BigDecimal price;
    private Date createTime;
    private String consumeType; // 消费方式 1-菜品 2-酒品

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }
}
