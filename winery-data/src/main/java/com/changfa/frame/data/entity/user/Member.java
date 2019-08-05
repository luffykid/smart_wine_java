package com.changfa.frame.data.entity.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "m_member")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Member {
    private Long id;
    private Long mbrLevelId;//会员等级ID
    private Long wineryId;//酒庄ID
    private String token;//令牌
    private String nickName;//昵称
    private String phone;//手机号
    private String wechat;//微信号
    private String openId;//微信openId
    private String userIcon;//用户头像
    private String status;//状态：0禁用；1正常
    private Date statusTime;//状态时间
    private Date createTime;//创建时间

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Basic
    @Column(name = "mbr_level_id")
    public Long getMbrLevelId() {
        return mbrLevelId;
    }

    public void setMbrLevelId(Long mbrLevelId) {
        this.mbrLevelId = mbrLevelId;
    }

    @Basic
    @Column(name = "winery_id")
    public Long getWineryId() {
        return wineryId;
    }

    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "wechat")
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Basic
    @Column(name = "open_id")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Basic
    @Column(name = "user_icon")
    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "status_time")
    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
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
        Member user = (Member) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(token, user.token) &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(wineryId, user.wineryId) &&
                Objects.equals(mbrLevelId, user.mbrLevelId) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(wechat, user.wechat) &&
                Objects.equals(openId, user.openId) &&
                Objects.equals(userIcon, user.userIcon) &&
                Objects.equals(status, user.status) &&
                Objects.equals(statusTime, user.statusTime) &&
                Objects.equals(createTime, user.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, token, nickName, wineryId, mbrLevelId ,phone, wechat, openId, userIcon, status, statusTime, createTime);
    }
}
