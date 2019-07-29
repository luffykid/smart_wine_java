package com.changfa.frame.data.entity.offline;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "offline_order_voucher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class OfflineOrderVoucher {
    private Integer id;
    private Integer offlineOrderId;
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
    @Column(name = "offline_order_id")
    public Integer getOfflineOrderId() {
        return offlineOrderId;
    }

    public void setOfflineOrderId(Integer offlineOrderId) {
        this.offlineOrderId = offlineOrderId;
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
        OfflineOrderVoucher that = (OfflineOrderVoucher) o;
        return id == that.id &&
                offlineOrderId == that.offlineOrderId &&
                Objects.equals(voucherInstId, that.voucherInstId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, offlineOrderId, voucherInstId);
    }
}
