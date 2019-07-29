package com.changfa.frame.data.entity.assemble;

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
@Table(name = "assemble_list")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class AssembleList {
    private Integer id;
    private String  assembleNo;
    private Integer assembleCommodity;
    private Integer master;
    private Integer assembleStatus;
    private Date createTime;
    private Integer isDelete;

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
    @Column(name = "assemble_no")
    public String getAssembleNo() {
        return assembleNo;
    }

    public void setAssembleNo(String assembleNo) {
        this.assembleNo = assembleNo;
    }

    @Basic
    @Column(name = "assemble_commodity")
    public Integer getAssembleCommodity() {
        return assembleCommodity;
    }

    public void setAssembleCommodity(Integer assembleCommodity) {
        this.assembleCommodity = assembleCommodity;
    }
    @Basic
    @Column(name = "master")
    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }
    @Basic
    @Column(name = "assemble_status")
    public Integer getAssembleStatus() {
        return assembleStatus;
    }

    public void setAssembleStatus(Integer assembleStatus) {
        this.assembleStatus = assembleStatus;
    }
    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssembleList that = (AssembleList) o;

        if (id != that.id ) return false;
        if (assembleNo != null ? !assembleNo.equals(that.assembleNo) : that.assembleNo != null) return false;
        if (assembleCommodity != null ? !assembleCommodity.equals(that.assembleCommodity) : that.assembleCommodity != null)
            return false;
        if (master != null ? !master.equals(that.master) : that.master != null) return false;
        if (assembleStatus != null ? !assembleStatus.equals(that.assembleStatus) : that.assembleStatus != null)
            return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id ;
        result = 31 * result + (assembleNo != null ? assembleNo.hashCode() : 0);
        result = 31 * result + (assembleCommodity != null ? assembleCommodity.hashCode() : 0);
        result = 31 * result + (master != null ? master.hashCode() : 0);
        result = 31 * result + (assembleStatus != null ? assembleStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }
}
