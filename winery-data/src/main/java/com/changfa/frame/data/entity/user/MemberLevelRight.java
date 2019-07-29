package com.changfa.frame.data.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/10/12 0012.
 */
@Entity
@Table(name = "member_level_right")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class MemberLevelRight {
    private Integer id;
    private Integer memberLevelId;
    private BigDecimal consumptionAmount;
    private Integer point;
    private String isFreightFree;

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
    @Column(name = "member_level_id")
    public Integer getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(Integer memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    @Basic
    @Column(name = "consumption_amount")
    public BigDecimal getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(BigDecimal consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
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
    @Column(name = "is_freight_free")
    public String getIsFreightFree() {
        return isFreightFree;
    }

    public void setIsFreightFree(String isFreightFree) {
        this.isFreightFree = isFreightFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberLevelRight that = (MemberLevelRight) o;

        if (id != that.id) return false;
        if (memberLevelId != that.memberLevelId) return false;
        if (consumptionAmount != null ? !consumptionAmount.equals(that.consumptionAmount) : that.consumptionAmount != null)
            return false;
        if (point != null ? !point.equals(that.point) : that.point != null) return false;
        if (isFreightFree != null ? !isFreightFree.equals(that.isFreightFree) : that.isFreightFree != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id;
        result = 31 * result + memberLevelId;
        result = 31 * result + (consumptionAmount != null ? consumptionAmount.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        result = 31 * result + (isFreightFree != null ? isFreightFree.hashCode() : 0);
        return result;
    }
}
