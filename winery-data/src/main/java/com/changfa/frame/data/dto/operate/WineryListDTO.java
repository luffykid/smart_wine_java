package com.changfa.frame.data.dto.operate;

import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */

public class WineryListDTO {
    private Integer id; //酒庄id
    private String wineryName;//酒庄名称
    private String userName;//超级管理员用户名
    private String address;//酒庄地址
    private String appName;//酒庄小程序名称
    private Integer workerNum; //工作人员数量

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(Integer workerNum) {
        this.workerNum = workerNum;
    }
}
