package com.changfa.frame.data.entity.activity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "qr_code_activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class QrCodeActivity {
    private Integer id;
    private Integer qrCodeUrlId;
    private Integer wineryQRCodeId;
    private String type;
    private Integer wineryId;
    private Integer prodId;
    private Integer themeId;
    private Integer activityId;
    private String status;
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
    @Column(name = "qr_code_url_id")
    public Integer getQrCodeUrlId() {
        return qrCodeUrlId;
    }

    public void setQrCodeUrlId(Integer qrCodeUrlId) {
        this.qrCodeUrlId = qrCodeUrlId;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "activity_id")
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "prod_id")
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    @Basic
    @Column(name = "theme_id")
    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
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
    @Column(name = "winery_qr_code_id")
    public Integer getWineryQRCodeId() {
        return wineryQRCodeId;
    }

    public void setWineryQRCodeId(Integer wineryQRCodeId) {
        this.wineryQRCodeId = wineryQRCodeId;
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
        QrCodeActivity that = (QrCodeActivity) o;
        return id == that.id &&
                Objects.equals(qrCodeUrlId, that.qrCodeUrlId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(wineryId, that.wineryId) &&
                Objects.equals(activityId, that.activityId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, qrCodeUrlId, type,  wineryId, activityId, status, createTime);
    }
}
