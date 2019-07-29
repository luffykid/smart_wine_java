package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserDTO implements Serializable {

    private Integer userId;
    private String userIcon;
    private String name;
    private String sex;
    private Integer age;
    private String phone;
    private Integer point;
    private BigDecimal balance;
    private Integer voucherNum;
    private String level;
    private String token;
    private Integer wineryId;
    private String openId;
    private String nickName;
    private String birthday;
    private Integer wineQuantity;
    private BigDecimal winePrice;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(Integer voucherNum) {
        this.voucherNum = voucherNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getWineQuantity() {
        return wineQuantity;
    }

    public void setWineQuantity(Integer wineQuantity) {
        this.wineQuantity = wineQuantity;
    }

    public BigDecimal getWinePrice() {
        return winePrice;
    }

    public void setWinePrice(BigDecimal winePrice) {
        this.winePrice = winePrice;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userIcon='" + userIcon + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", point=" + point +
                ", balance=" + balance +
                ", voucherNum=" + voucherNum +
                ", level='" + level + '\'' +
                ", token='" + token + '\'' +
                ", wineryId=" + wineryId +
                ", openId='" + openId + '\'' +
                '}';
    }
}
