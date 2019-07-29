package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleWeichatDTO {
    private Integer id;//拼团商品id
    private String userLogo;//用户头像
    private String prodLogo;//商品图片
    private String prodTitle; //标题
    private String prodSpecName; //规格
    private BigDecimal assemblePrice;//团购价格
    private Integer prodNum;//团购的商品数量
    private String delivery; //配送方式
    private String message; //留言
    private BigDecimal totalPrie;//合计
    private BigDecimal prodPrie;//商品金额
    private BigDecimal freight;//运费
    private List<AssembleUserWeichatListDTO> userList; //已经参团的用户

    public List<AssembleUserWeichatListDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<AssembleUserWeichatListDTO> userList) {
        this.userList = userList;
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

    public String getProdSpecName() {
        return prodSpecName;
    }

    public void setProdSpecName(String prodSpecName) {
        this.prodSpecName = prodSpecName;
    }

    public BigDecimal getAssemblePrice() {
        return assemblePrice;
    }

    public void setAssemblePrice(BigDecimal assemblePrice) {
        this.assemblePrice = assemblePrice;
    }

    public Integer getProdNum() {
        return prodNum;
    }

    public void setProdNum(Integer prodNum) {
        this.prodNum = prodNum;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getTotalPrie() {
        return totalPrie;
    }

    public void setTotalPrie(BigDecimal totalPrie) {
        this.totalPrie = totalPrie;
    }

    public BigDecimal getProdPrie() {
        return prodPrie;
    }

    public void setProdPrie(BigDecimal prodPrie) {
        this.prodPrie = prodPrie;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }
}
