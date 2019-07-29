package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class BargainingUserWeichatListDTO {
    private Integer id;//帮砍价记录表id
    private Integer userID;//用户id
    private String icon;//头像
    private BigDecimal lessPrice; //帮砍掉价格
    private Date createTime;

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

    public BigDecimal getLessPrice() {
        return lessPrice;
    }

    public void setLessPrice(BigDecimal lessPrice) {
        this.lessPrice = lessPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
