package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/14.
 */
public class BargainingPordDetailWeichatDTO {
    private Integer id;    //砍价商品表id
    private String prodPrice; //原价
    private BigDecimal bargainingPrice;//最低价
    private Date endTime;//最终活动结束的时间点
    private String prodSpecName; //规格
    private String prodName;  //商品名称
    private String prodTitle; //商品标题
    private Integer bargainingNumEd; //已经发起砍价的数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProdSpecName() {
        return prodSpecName;
    }

    public void setProdSpecName(String prodSpecName) {
        this.prodSpecName = prodSpecName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public Integer getBargainingNumEd() {
        return bargainingNumEd;
    }

    public void setBargainingNumEd(Integer bargainingNumEd) {
        this.bargainingNumEd = bargainingNumEd;
    }
}
