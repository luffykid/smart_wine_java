package com.changfa.frame.data.entity.bargaining;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/14.
 */
@Entity
@Table(name = "bargaining_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class BargainingUser {
    private Integer id;
    private Integer userId;
    private Integer bargainingCommodity;
    private BigDecimal buyPrice;
    private Integer orderId;
    private Date createTime;
    private Integer status;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Basic
    @Column(name = "bargaining_commodity")
    public Integer getBargainingCommodity() {
        return bargainingCommodity;
    }

    public void setBargainingCommodity(Integer bargainingCommodity) {
        this.bargainingCommodity = bargainingCommodity;
    }
    @Basic
    @Column(name = "buy_price")
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
    @Basic
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BargainingUser that = (BargainingUser) o;

        if (id != that.id ) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (bargainingCommodity != null ? !bargainingCommodity.equals(that.bargainingCommodity) : that.bargainingCommodity != null)
            return false;
        if (buyPrice != null ? !buyPrice.equals(that.buyPrice) : that.buyPrice != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id ;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (bargainingCommodity != null ? bargainingCommodity.hashCode() : 0);
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
