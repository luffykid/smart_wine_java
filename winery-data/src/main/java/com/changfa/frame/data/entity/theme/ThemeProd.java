package com.changfa.frame.data.entity.theme;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/6.
 */
@Entity
@Table(name = "theme_prod")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ThemeProd {
    private Integer id;
    private Integer wineryId;
    private Integer themeId;
    private Integer prodId;
    private Date createTime;
    private String prodLogo;
    private String prodLogoName;
    private String descri;

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
    @Column(name = "winery_id")
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "prod_logo_name")
    public String getProdLogoName() {
        return prodLogoName;
    }

    public void setProdLogoName(String prodLogoName) {
        this.prodLogoName = prodLogoName;
    }


    @Basic
    @Column(name = "prod_logo")
    public String getProdLogo() {
        return prodLogo;
    }

    public void setProdLogo(String prodLogo) {
        this.prodLogo = prodLogo;
    }

    @Basic
    @Column(name = "descri")
    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
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
    @Column(name = "prod_id")
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
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

        ThemeProd that = (ThemeProd) o;

        if (id != that.id) return false;
        if (wineryId != null ? !wineryId.equals(that.wineryId) : that.wineryId != null) return false;
        if (themeId != null ? !themeId.equals(that.themeId) : that.themeId != null) return false;
        if (prodId != null ? !prodId.equals(that.prodId) : that.prodId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (wineryId != null ? wineryId.hashCode() : 0);
        result = 31 * result + (themeId != null ? themeId.hashCode() : 0);
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
