package com.changfa.frame.data.entity.point;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "point_to_voucher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class PointToVoucher {
    private Integer id;
    private String orderNo;
    private int userId;
    private Integer point;
    private Integer wineryId;
    private Integer voucherInstId;
    private Date createTime;


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
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "point")
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Basic
    @Column(name = "winery_id")
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "voucher_inst_id")
    public Integer getVoucherInstId() {
        return voucherInstId;
    }

    public void setVoucherInstId(Integer voucherInstId) {
        this.voucherInstId = voucherInstId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointToVoucher that = (PointToVoucher) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(point, that.point) &&
                Objects.equals(voucherInstId, that.voucherInstId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderNo, userId, point, voucherInstId, createTime);
    }
}
