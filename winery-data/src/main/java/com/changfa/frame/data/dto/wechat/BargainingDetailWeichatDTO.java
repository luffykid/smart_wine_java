package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/11/14.
 */
public class BargainingDetailWeichatDTO {
    private Integer id;    //砍价记录表id
    private Integer userId; //发起砍价用户id
    private String userLogo;// 发起砍价用户头像
    private String prodName;//商品名称
    private String prodLogo;//商品图片
    private String prodTitle; //标题
    private String prodPrice; //原价
    private BigDecimal bargainingPrice;//最低价

    private BigDecimal nowPrice;//当前价格
    private List<BargainingUserWeichatListDTO> userList; //帮砍价的用户
    List<Integer> helpUserIdList;//帮砍价用户的id集合



    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdLogo() {
        return prodLogo;
    }

    public void setProdLogo(String prodLogo) {
        this.prodLogo = prodLogo;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getHelpUserIdList() {
        return helpUserIdList;
    }

    public void setHelpUserIdList(List<Integer> helpUserIdList) {
        this.helpUserIdList = helpUserIdList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public BigDecimal getBargainingPrice() {
        return bargainingPrice;
    }

    public void setBargainingPrice(BigDecimal bargainingPrice) {
        this.bargainingPrice = bargainingPrice;
    }

    public BigDecimal getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(BigDecimal nowPrice) {
        this.nowPrice = nowPrice;
    }

    public List<BargainingUserWeichatListDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<BargainingUserWeichatListDTO> userList) {
        this.userList = userList;
    }
}
