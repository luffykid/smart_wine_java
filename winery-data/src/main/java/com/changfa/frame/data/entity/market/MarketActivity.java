package com.changfa.frame.data.entity.market;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@Entity
@Table(name = "market_activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class MarketActivity {
    private Integer id;
    private Integer marketActivityTypeId;
    private Integer wineryId;
    private Integer createUserId;
    private String name;
    private String sendVoucherRemind;
    private String expireRemind;
    private Date beginTime;
    private Date endTime;
    private String ruleDescri;
    private BigDecimal moneyPerBill;
    private BigDecimal moneyTotalBill;
    private String url;
    private String status;
    private Date updateTime;
    private Date statusTime;
    private Date createTime;
    private String sendSetting;
    private String sendType;
    private String QrCode;
    private String isSendSms;
    private Integer sendPoint;
    private String isLimit;
    private Integer limitUser;

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
    @Column(name = "send_setting")
    public String getSendSetting() {
        return sendSetting;
    }

    public void setSendSetting(String sendSetting) {
        this.sendSetting = sendSetting;
    }

    @Basic
    @Column(name = "send_type")
    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    @Basic
    @Column(name = "market_activity_type_id")
    public Integer getMarketActivityTypeId() {
        return marketActivityTypeId;
    }

    public void setMarketActivityTypeId(Integer marketActivityTypeId) {
        this.marketActivityTypeId = marketActivityTypeId;
    }

    @Basic
    @Column(name = "winery_id")
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "create_user_id")
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "send_voucher_remind")
    public String getSendVoucherRemind() {
        return sendVoucherRemind;
    }

    public void setSendVoucherRemind(String sendVoucherRemind) {
        this.sendVoucherRemind = sendVoucherRemind;
    }

    @Basic
    @Column(name = "expire_remind")
    public String getExpireRemind() {
        return expireRemind;
    }

    public void setExpireRemind(String expireRemind) {
        this.expireRemind = expireRemind;
    }

    @Basic
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "rule_descri")
    public String getRuleDescri() {
        return ruleDescri;
    }

    public void setRuleDescri(String ruleDescri) {
        this.ruleDescri = ruleDescri;
    }

    @Basic
    @Column(name = "qr_code")
    public String getQrCode() {
        return QrCode;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }

    @Basic
    @Column(name = "money_per_bill")
    public BigDecimal getMoneyPerBill() {
        return moneyPerBill;
    }

    public void setMoneyPerBill(BigDecimal moneyPerBill) {
        this.moneyPerBill = moneyPerBill;
    }

    @Basic
    @Column(name = "money_total_bill")
    public BigDecimal getMoneyTotalBill() {
        return moneyTotalBill;
    }

    public void setMoneyTotalBill(BigDecimal moneyTotalBill) {
        this.moneyTotalBill = moneyTotalBill;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    @Basic
    @Column(name = "is_send_sms")
    public String getIsSendSms() {
        return isSendSms;
    }

    public void setIsSendSms(String isSendSms) {
        this.isSendSms = isSendSms;
    }

    @Basic
    @Column(name = "send_point")
    public Integer getSendPoint() {
        return sendPoint;
    }

    public void setSendPoint(Integer sendPoint) {
        this.sendPoint = sendPoint;
    }

    @Basic
    @Column(name = "is_limit")
    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    @Basic
    @Column(name = "limit_user")
    public Integer getLimitUser() {
        return limitUser;
    }

    public void setLimitUser(Integer limitUser) {
        this.limitUser = limitUser;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketActivity that = (MarketActivity) o;

        if (id != that.id) return false;
        if (marketActivityTypeId != that.marketActivityTypeId) return false;
        if (wineryId != that.wineryId) return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId) : that.createUserId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sendVoucherRemind != null ? !sendVoucherRemind.equals(that.sendVoucherRemind) : that.sendVoucherRemind != null)
            return false;
        if (expireRemind != null ? !expireRemind.equals(that.expireRemind) : that.expireRemind != null) return false;
        if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (ruleDescri != null ? !ruleDescri.equals(that.ruleDescri) : that.ruleDescri != null) return false;
        if (moneyPerBill != null ? !moneyPerBill.equals(that.moneyPerBill) : that.moneyPerBill != null) return false;
        if (moneyTotalBill != null ? !moneyTotalBill.equals(that.moneyTotalBill) : that.moneyTotalBill != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (statusTime != null ? !statusTime.equals(that.statusTime) : that.statusTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + marketActivityTypeId;
        result = 31 * result + wineryId;
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sendVoucherRemind != null ? sendVoucherRemind.hashCode() : 0);
        result = 31 * result + (expireRemind != null ? expireRemind.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (ruleDescri != null ? ruleDescri.hashCode() : 0);
        result = 31 * result + (moneyPerBill != null ? moneyPerBill.hashCode() : 0);
        result = 31 * result + (moneyTotalBill != null ? moneyTotalBill.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (statusTime != null ? statusTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
