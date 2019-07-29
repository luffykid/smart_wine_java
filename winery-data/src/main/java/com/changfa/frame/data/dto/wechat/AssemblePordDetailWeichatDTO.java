package com.changfa.frame.data.dto.wechat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssemblePordDetailWeichatDTO {
    private Integer id; //拼团商品 id
    private BigDecimal assemblePrice;//拼团价格
    private String prodPrice; //原价
    private Date endTime;//最终活动结束的时间点
    private Integer assemblePreson;//拼团人数
    private String prodSpecName; //规格
    private Integer assemblePresonEd; //已拼团人数  （区分是同一个团队的  还是同一个商品的)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAssemblePrice() {
        return assemblePrice;
    }

    public void setAssemblePrice(BigDecimal assemblePrice) {
        this.assemblePrice = assemblePrice;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
    }

    public String getProdSpecName() {
        return prodSpecName;
    }

    public void setProdSpecName(String prodSpecName) {
        this.prodSpecName = prodSpecName;
    }

    public Integer getAssemblePresonEd() {
        return assemblePresonEd;
    }

    public void setAssemblePresonEd(Integer assemblePresonEd) {
        this.assemblePresonEd = assemblePresonEd;
    }
}
