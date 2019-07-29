package com.changfa.frame.data.entity.assemble;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/14.
 */
@Entity
@Table(name = "admin_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AssembleUser {
    private Integer id;
    private Integer  userId;
    private Integer isMaster;
    private Integer assembleList;
    private Integer orderId;
    private Date createTime;
    private Integer assembleCommodity;

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
    @Column(name = "is_master")
    public Integer getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }
    @Basic
    @Column(name = "assemble_list")
    public Integer getAssembleList() {
        return assembleList;
    }

    public void setAssembleList(Integer assembleList) {
        this.assembleList = assembleList;
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

    public Integer getAssembleCommodity() {
        return assembleCommodity;
    }

    public void setAssembleCommodity(Integer assembleCommodity) {
        this.assembleCommodity = assembleCommodity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssembleUser that = (AssembleUser) o;

        if (id != that.id ) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (isMaster != null ? !isMaster.equals(that.isMaster) : that.isMaster != null) return false;
        if (assembleList != null ? !assembleList.equals(that.assembleList) : that.assembleList != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id ;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isMaster != null ? isMaster.hashCode() : 0);
        result = 31 * result + (assembleList != null ? assembleList.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (assembleCommodity != null ? assembleCommodity.hashCode() : 0);
        return result;
    }
}
