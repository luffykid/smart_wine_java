package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageDTO implements Serializable {

    private String token;
    private String rangeType;
    private String[] phoneList;
    private Integer smsTempId;
    private String messageOrderNo;
    private String smsTempName;
    private Integer id;
    private Integer wineryId;
    private String title;
    private String content;
    private String picture;
    private Integer adminUserId;
    private String sendType;
    private String status;
    private Date statusTime;
    private Date createTime;
    private String type;
    private String[] levelList;
    private List<String> levelName;
    private List<String> userName;
    private String createUserName;
    private String smsTempParaName;
    private String smsTempParaCode;
    private Map<String,Object> smsTempParaNameList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSmsTempId() {
        return smsTempId;
    }

    public void setSmsTempId(Integer smsTempId) {
        this.smsTempId = smsTempId;
    }

    public String getMessageOrderNo() {
        return messageOrderNo;
    }

    public void setMessageOrderNo(String messageOrderNo) {
        this.messageOrderNo = messageOrderNo;
    }

    public String getSmsTempName() {
        return smsTempName;
    }

    public void setSmsTempName(String smsTempName) {
        this.smsTempName = smsTempName;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getLevelList() {
        return levelList;
    }

    public void setLevelList(String[] levelList) {
        this.levelList = levelList;
    }

    public List<String> getLevelName() {
        return levelName;
    }

    public void setLevelName(List<String> levelName) {
        this.levelName = levelName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getSmsTempParaName() {
        return smsTempParaName;
    }

    public void setSmsTempParaName(String smsTempParaName) {
        this.smsTempParaName = smsTempParaName;
    }

    public String getSmsTempParaCode() {
        return smsTempParaCode;
    }

    public void setSmsTempParaCode(String smsTempParaCode) {
        this.smsTempParaCode = smsTempParaCode;
    }

    public String getRangeType() {
        return rangeType;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public String[] getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(String[] phoneList) {
        this.phoneList = phoneList;
    }

    public Map<String, Object> getSmsTempParaNameList() {
        return smsTempParaNameList;
    }

    public void setSmsTempParaNameList(Map<String, Object> smsTempParaNameList) {
        this.smsTempParaNameList = smsTempParaNameList;
    }

    public List<String> getUserName() {
        return userName;
    }

    public void setUserName(List<String> userName) {
        this.userName = userName;
    }
}
