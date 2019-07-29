package com.changfa.frame.data.dto.saas;

/**
 * Created by Administrator on 2018/10/29.
 */
public class PointDTO {
    private Integer id;
    private String type;
    private String status;//停用启用
    private String name;//名称
    private String rule;//奖励规则
    private String range;//奖励范围
    private String time;//有效期
    private String upperLimit;//获取上线
    private String offlinePoint;//门店消费抵现
    private String onlinePoint;//线上消费抵现
    private String gift;//积分换礼
    private String descri;//限制与说明

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getOfflinePoint() {
        return offlinePoint;
    }

    public void setOfflinePoint(String offlinePoint) {
        this.offlinePoint = offlinePoint;
    }

    public String getOnlinePoint() {
        return onlinePoint;
    }

    public void setOnlinePoint(String onlinePoint) {
        this.onlinePoint = onlinePoint;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }
}
