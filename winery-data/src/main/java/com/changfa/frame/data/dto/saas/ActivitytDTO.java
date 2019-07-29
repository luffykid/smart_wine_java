package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/17 0017.
 */
public class ActivitytDTO implements Serializable{
    private Integer id;
    private String token;
    private String eventName;
    private String content;
    private List<String> picurl;
    private List<String> video;
    private List<Map<String,Integer>> voucherId;
    private List<Map<String,String>> voucherName;
    private List<String> level;
    private List<Integer> levelId;
    private List<String> activiTime;
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private String quota;
    private String money;
    private List<String> province;
    private List<String> provinceName;
    private String detailAddress;
    private String erweima;
    private String banner;
    private List<Map<String,String>> bannerName;

    public List<Map<String, String>> getBannerName() {
        return bannerName;
    }

    public void setBannerName(List<Map<String, String>> bannerName) {
        this.bannerName = bannerName;
    }

    public List<String> getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(List<String> provinceName) {
        this.provinceName = provinceName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getLevelId() {
        return levelId;
    }

    public void setLevelId(List<Integer> levelId) {
        this.levelId = levelId;
    }

    public List<Map<String, Integer>> getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(List<Map<String, Integer>> voucherId) {
        this.voucherId = voucherId;
    }

    public List<Map<String, String>> getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(List<Map<String, String>> voucherName) {
        this.voucherName = voucherName;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getErweima() {
        return erweima;
    }

    public void setErweima(String erweima) {
        this.erweima = erweima;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicurl() {
        return picurl;
    }

    public void setPicurl(List<String> picurl) {
        this.picurl = picurl;
    }

    public List<String> getVideo() {
        return video;
    }

    public void setVideo(List<String> video) {
        this.video = video;
    }

    public List<String> getLevel() {
        return level;
    }

    public void setLevel(List<String> level) {
        this.level = level;
    }

    public List<String> getActiviTime() {
        return activiTime;
    }

    public void setActiviTime(List<String> activiTime) {
        this.activiTime = activiTime;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<String> getProvince() {
        return province;
    }

    public void setProvince(List<String> province) {
        this.province = province;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

}
