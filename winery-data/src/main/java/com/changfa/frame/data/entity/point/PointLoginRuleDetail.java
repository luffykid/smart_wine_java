package com.changfa.frame.data.entity.point;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/11/1.
 */
@Entity
@Table(name = "point_login_rule_detail")
public class PointLoginRuleDetail {
    private Integer id;
    private Integer pointLoginRuleId;
    private Integer day;
    private Integer point;

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
    @Column(name = "point_login_rule_id")
    public Integer getPointLoginRuleId() {
        return pointLoginRuleId;
    }

    public void setPointLoginRuleId(int pointLoginRuleId) {
        this.pointLoginRuleId = pointLoginRuleId;
    }

    @Basic
    @Column(name = "day")
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Basic
    @Column(name = "point")
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointLoginRuleDetail that = (PointLoginRuleDetail) o;

        if (id != that.id) return false;
        if (pointLoginRuleId != that.pointLoginRuleId) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (point != null ? !point.equals(that.point) : that.point != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + pointLoginRuleId;
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        return result;
    }
}
