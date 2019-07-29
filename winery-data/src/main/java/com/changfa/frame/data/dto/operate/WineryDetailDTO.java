package com.changfa.frame.data.dto.operate;

import com.changfa.frame.data.entity.user.Role;

import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */

public class WineryDetailDTO {
    private Integer id; //酒庄id
    private String wineryName;//酒庄名称
    private String address;//酒庄地址

    //超级管理员信息
    private String userName;//超级管理员用户名
    private String phone;//超级管理员手机号码
    private List<Role> roleAllList; //所有可用角色
    private List<Role> roleEdList; //用户选定的角色



    //绑定小程序信息
    private String appId;
    private String appSecret;
    private String wxPayId;
    private String wxPayKey;
    private String domainName;
    private String qrCodeUrl;
    private String appName; // 小程序名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWineryName() {
        return wineryName;
    }

    public void setWineryName(String wineryName) {
        this.wineryName = wineryName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Role> getRoleAllList() {
        return roleAllList;
    }

    public void setRoleAllList(List<Role> roleAllList) {
        this.roleAllList = roleAllList;
    }

    public List<Role> getRoleEdList() {
        return roleEdList;
    }

    public void setRoleEdList(List<Role> roleEdList) {
        this.roleEdList = roleEdList;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getWxPayId() {
        return wxPayId;
    }

    public void setWxPayId(String wxPayId) {
        this.wxPayId = wxPayId;
    }

    public String getWxPayKey() {
        return wxPayKey;
    }

    public void setWxPayKey(String wxPayKey) {
        this.wxPayKey = wxPayKey;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
