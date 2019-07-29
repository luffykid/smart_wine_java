package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleUserWeichatListDTO {
    private Integer id;//拼团用户表id
    private Integer userID;//用户id
    private String icon;//头像
    private Integer isMaster;//是否为团长 1是 0 不是
    private String status; //待支付P,代发货F,待收货H,待评价R,已完成S,已取消D，E：已失效

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
