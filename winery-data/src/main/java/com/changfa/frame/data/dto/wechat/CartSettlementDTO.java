package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/8.
 */
public class CartSettlementDTO {
    private String token;
    private Integer userAddressId;
    private String userName;
    private String phone;
    private String address;
    private List<NewProdListDTO> newProdLists;
    private String expressMethod;//配送方式
    private String expressPrice;//运费
    private String totalPrice;//商品金额
    private String totalPoint;//商品积分
    private Map<String,Object> voucherName;//优惠券
    private BigDecimal balance;

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getExpressMethod() {
        return expressMethod;
    }

    public void setExpressMethod(String expressMethod) {
        this.expressMethod = expressMethod;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<String, Object> getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(Map<String, Object> voucherName) {
        this.voucherName = voucherName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<NewProdListDTO> getNewProdLists() {
        return newProdLists;
    }

    public void setNewProdLists(List<NewProdListDTO> newProdLists) {
        this.newProdLists = newProdLists;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
