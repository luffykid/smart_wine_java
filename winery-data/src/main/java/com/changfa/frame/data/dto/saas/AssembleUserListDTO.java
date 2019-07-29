package com.changfa.frame.data.dto.saas;

import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleUserListDTO {
    private Integer id;//拼团用户表id
    private Integer userID;//用户id
    private String icon;//头像
    private String name;//用户昵称
    private Date createTime;
    private String orderNo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
