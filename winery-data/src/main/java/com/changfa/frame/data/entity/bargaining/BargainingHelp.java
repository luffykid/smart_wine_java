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
@Table(name = "bargaining_help")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class BargainingHelp {
    private Integer id;
    private Integer userId;
    private Integer bargainingUser;
    private BigDecimal lessPrice;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Basic
    @Column(name = "bargaining_user")
    public Integer getBargainingUser() {
        return bargainingUser;
    }

    public void setBargainingUser(Integer bargainingUser) {
        this.bargainingUser = bargainingUser;
    }

	@Basic
	@Column(name = "less_price")
	public BigDecimal getLessPrice() {
		return lessPrice;
	}

	public void setLessPrice(BigDecimal lessPrice) {
		this.lessPrice = lessPrice;
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

        BargainingHelp that = (BargainingHelp) o;

        if (id != that.id ) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (bargainingUser != null ? !bargainingUser.equals(that.bargainingUser) : that.bargainingUser != null)
            return false;
        if (lessPrice != null ? !lessPrice.equals(that.lessPrice) : that.lessPrice != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (bargainingUser != null ? bargainingUser.hashCode() : 0);
        result = 31 * result + (lessPrice != null ? lessPrice.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

}
