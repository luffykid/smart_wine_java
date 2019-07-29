package com.changfa.frame.data.entity.activity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "activity_order_voucher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ActivityOrderVoucher {
    private Integer id;
    private Integer activityOrderId;
    private Integer voucherInstId;

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
    @Column(name = "activity_order_id")
    public Integer getActivityOrderId() {
        return activityOrderId;
    }

    public void setActivityOrderId(Integer activityOrderId) {
        this.activityOrderId = activityOrderId;
    }

    @Basic
    @Column(name = "voucher_inst_id")
    public Integer getVoucherInstId() {
        return voucherInstId;
    }

    public void setVoucherInstId(Integer voucherInstId) {
        this.voucherInstId = voucherInstId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityOrderVoucher that = (ActivityOrderVoucher) o;
        return id == that.id &&
                activityOrderId == that.activityOrderId &&
                Objects.equals(voucherInstId, that.voucherInstId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, activityOrderId, voucherInstId);
    }
}
