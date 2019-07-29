package com.changfa.frame.data.dto.saas;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */
public class PointRewardRuleDTO {
    private Integer id;
    private String token;
    private String name;//名称
    private String depositMoney;//充值多少金额送一积分
    private String consumeMone;//消费多少金额送一积分
    private String isLongTime;//任务时间  长期Y  自定义N
    private List<String> beginTime;//积分规则开始结束时间
    private String beginTim;//积分规则开始时间
    private String endTim;//积分规则结束时间
    private String isLimit;//获取上限   长期Y  自定义N
    private Integer everyDayLimit;//每天几次
    private Integer onlinePoin;//积分门店消费抵现
    private Integer offlinePoint;//积分门店消费抵现
    private List<PointRewardRuleItemDTO> pointRewardRuleDTOS;//积分换礼
    private String descri;//说明
    private boolean checked1;
    private boolean checked2;
    private boolean checked3;

    public boolean isChecked1() {
        return checked1;
    }

    public void setChecked1(boolean checked1) {
        this.checked1 = checked1;
    }

    public boolean isChecked2() {
        return checked2;
    }

    public void setChecked2(boolean checked2) {
        this.checked2 = checked2;
    }

    public boolean isChecked3() {
        return checked3;
    }

    public void setChecked3(boolean checked3) {
        this.checked3 = checked3;
    }

    public String getBeginTim() {
        return beginTim;
    }

    public void setBeginTim(String beginTim) {
        this.beginTim = beginTim;
    }

    public String getEndTim() {
        return endTim;
    }

    public void setEndTim(String endTim) {
        this.endTim = endTim;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(String depositMoney) {
        this.depositMoney = depositMoney;
    }

    public String getConsumeMone() {
        return consumeMone;
    }

    public void setConsumeMone(String consumeMone) {
        this.consumeMone = consumeMone;
    }

    public String getIsLongTime() {
        return isLongTime;
    }

    public void setIsLongTime(String isLongTime) {
        this.isLongTime = isLongTime;
    }

    public List<String> getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(List<String> beginTime) {
        this.beginTime = beginTime;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getEveryDayLimit() {
        return everyDayLimit;
    }

    public void setEveryDayLimit(Integer everyDayLimit) {
        this.everyDayLimit = everyDayLimit;
    }

    public Integer getOnlinePoin() {
        return onlinePoin;
    }

    public void setOnlinePoin(Integer onlinePoin) {
        this.onlinePoin = onlinePoin;
    }

    public Integer getOfflinePoint() {
        return offlinePoint;
    }

    public void setOfflinePoint(Integer offlinePoint) {
        this.offlinePoint = offlinePoint;
    }

    public List<PointRewardRuleItemDTO> getPointRewardRuleDTOS() {
        return pointRewardRuleDTOS;
    }

    public void setPointRewardRuleDTOS(List<PointRewardRuleItemDTO> pointRewardRuleDTOS) {
        this.pointRewardRuleDTOS = pointRewardRuleDTOS;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }
}
