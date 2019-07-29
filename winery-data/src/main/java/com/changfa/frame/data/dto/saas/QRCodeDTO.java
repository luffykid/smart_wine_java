package com.changfa.frame.data.dto.saas;

import java.util.Date;

public class QRCodeDTO {
    private String token;
    private Integer id;
    private Integer wineryId;
    private String descri;
    private String url;
    private String status;
    private Date statusTime;
    private Date createTime;
    private String activityType;
    private Integer activityId;
    private String activityName;
    private Integer qrCodeUrlId;
    private Integer wineryQRCodeId;
    private String type;
    private Integer prodId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getQrCodeUrlId() {
        return qrCodeUrlId;
    }

    public void setQrCodeUrlId(Integer qrCodeUrlId) {
        this.qrCodeUrlId = qrCodeUrlId;
    }

    public Integer getWineryQRCodeId() {
        return wineryQRCodeId;
    }

    public void setWineryQRCodeId(Integer wineryQRCodeId) {
        this.wineryQRCodeId = wineryQRCodeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
