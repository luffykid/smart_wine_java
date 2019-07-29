package com.changfa.frame.data.dto.saas;

import com.changfa.frame.data.dto.wechat.AssembleUserWeichatListDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleListDetailDTO {
    private Integer id;//团队列表id
    private String assembleNo;//团购编号
    private Integer assembleStatus;
    private String masterName;
    private Date createTime;
    private String prodName;

    private List<AssembleUserListDTO> userList; //已经参团的用户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssembleNo() {
        return assembleNo;
    }

    public void setAssembleNo(String assembleNo) {
        this.assembleNo = assembleNo;
    }

    public Integer getAssembleStatus() {
        return assembleStatus;
    }

    public void setAssembleStatus(Integer assembleStatus) {
        this.assembleStatus = assembleStatus;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public List<AssembleUserListDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<AssembleUserListDTO> userList) {
        this.userList = userList;
    }
}
