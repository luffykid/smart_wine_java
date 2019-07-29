package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
public class GiveVoucherDTO implements Serializable {
    private Integer id;
    private Integer typeId;
    private String token;
    private Integer marketActivityTypeId;//模板类型
    private String name;
    private List<GiveVoucherItemDTO> giveVoucherItemList;//赠券
    private List<Map<String,Object>> mapList;//赠券下拉框
    private List<GiveVoucherItemDTO> giveVoucherItemList2;//赠券送券
    private String sendVoucherRemind;//发券提醒
    private String expireRemind;//券到期提醒
    private String[] beginTime;//开始时间和结束时间
    private String beginTime2;//开始时间和结束时间
    private String endTime2;//开始时间和结束时间
    private String state;//状态
    private String ruleDescri;//规则
    private List<Integer> level;//人群限制
    private String voucherSetup;//发券设置
    private String moneyPerBill;//只有满额赠券有值(单笔消费满额)
    private String moneyTotalBill;//累计消费满额
    private String sendMoney;//赠送多少钱
    private String content;//内容
    private List<String> picurl;//图片地址
    private String banner;
    private List<Map<String,String>> bannerName;
    private String sendSetting;//发圈设置 W循环赠送O，当次消费仅赠送一次
    private String sendType;//充值C送券S纯文案P
    private Integer sendPoint;
    private String isLimit;
    private Integer limit;

    public List<Map<String, Object>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, Object>> mapList) {
        this.mapList = mapList;
    }

    public List<GiveVoucherItemDTO> getGiveVoucherItemList2() {
        return giveVoucherItemList2;
    }

    public void setGiveVoucherItemList2(List<GiveVoucherItemDTO> giveVoucherItemList2) {
        this.giveVoucherItemList2 = giveVoucherItemList2;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public List<Map<String, String>> getBannerName() {
        return bannerName;
    }

    public void setBannerName(List<Map<String, String>> bannerName) {
        this.bannerName = bannerName;
    }

    public String getSendSetting() {
        return sendSetting;
    }

    public void setSendSetting(String sendSetting) {
        this.sendSetting = sendSetting;
    }

    public String getBeginTime2() {
        return beginTime2;
    }

    public void setBeginTime2(String beginTime2) {
        this.beginTime2 = beginTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getLevel() {
        return level;
    }

    public void setLevel(List<Integer> level) {
        this.level = level;
    }

    public List<String> getPicurl() {
        return picurl;
    }

    public void setPicurl(List<String> picurl) {
        this.picurl = picurl;
    }

    public String getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(String sendMoney) {
        this.sendMoney = sendMoney;
    }

    public String getSendVoucherRemind() {
        return sendVoucherRemind;
    }

    public void setSendVoucherRemind(String sendVoucherRemind) {
        this.sendVoucherRemind = sendVoucherRemind;
    }

    public String getExpireRemind() {
        return expireRemind;
    }

    public void setExpireRemind(String expireRemind) {
        this.expireRemind = expireRemind;
    }

    public String[] getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String[] beginTime) {
        this.beginTime = beginTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRuleDescri() {
        return ruleDescri;
    }

    public void setRuleDescri(String ruleDescri) {
        this.ruleDescri = ruleDescri;
    }

    public String getVoucherSetup() {
        return voucherSetup;
    }

    public void setVoucherSetup(String voucherSetup) {
        this.voucherSetup = voucherSetup;
    }

    public String getMoneyPerBill() {
        return moneyPerBill;
    }

    public void setMoneyPerBill(String moneyPerBill) {
        this.moneyPerBill = moneyPerBill;
    }

    public String getMoneyTotalBill() {
        return moneyTotalBill;
    }

    public void setMoneyTotalBill(String moneyTotalBill) {
        this.moneyTotalBill = moneyTotalBill;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<GiveVoucherItemDTO> getGiveVoucherItemList() {
        return giveVoucherItemList;
    }

    public void setGiveVoucherItemList(List<GiveVoucherItemDTO> giveVoucherItemList) {
        this.giveVoucherItemList = giveVoucherItemList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMarketActivityTypeId() {
        return marketActivityTypeId;
    }

    public void setMarketActivityTypeId(Integer marketActivityTypeId) {
        this.marketActivityTypeId = marketActivityTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSendPoint() {
        return sendPoint;
    }

    public void setSendPoint(Integer sendPoint) {
        this.sendPoint = sendPoint;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
