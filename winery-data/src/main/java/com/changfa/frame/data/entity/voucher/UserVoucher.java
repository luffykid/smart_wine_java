package com.changfa.frame.data.entity.voucher;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_voucher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class UserVoucher {
    private int id;
    private int userId;
    private int voucherInstId;
    private Date sendTime;
    private Date getTime;
    private Date useTime;
    private Date ineffectiveTime;
    private Date createTime;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "voucher_inst_id")
    public int getVoucherInstId() {
        return voucherInstId;
    }

    public void setVoucherInstId(int voucherInstId) {
        this.voucherInstId = voucherInstId;
    }

    @Basic
    @Column(name = "send_time")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "get_time")
    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    @Basic
    @Column(name = "use_time")
    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    @Basic
    @Column(name = "ineffective_time")
    public Date getIneffectiveTime() {
        return ineffectiveTime;
    }

    public void setIneffectiveTime(Date ineffectiveTime) {
        this.ineffectiveTime = ineffectiveTime;
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
        UserVoucher that = (UserVoucher) o;
        return id == that.id &&
                userId == that.userId &&
                voucherInstId == that.voucherInstId &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(getTime, that.getTime) &&
                Objects.equals(useTime, that.useTime) &&
                Objects.equals(ineffectiveTime, that.ineffectiveTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, voucherInstId, sendTime, getTime, useTime, ineffectiveTime, createTime);
    }
}
