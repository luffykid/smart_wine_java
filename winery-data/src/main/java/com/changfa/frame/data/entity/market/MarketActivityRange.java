package com.changfa.frame.data.entity.market;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@Entity
@Table(name = "market_activity_range")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class MarketActivityRange {
    private Integer id;
    private Integer wineryId;
    private Integer marketActivityId;
    private Integer memberLevelId;

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
    @Column(name = "market_activity_id")
    public Integer getMarketActivityId() {
        return marketActivityId;
    }

    public void setMarketActivityId(Integer marketActivityId) {
        this.marketActivityId = marketActivityId;
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
    @Column(name = "winery_id")
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketActivityRange that = (MarketActivityRange) o;

        if (id != that.id) return false;
        if (marketActivityId != that.marketActivityId) return false;
        if (memberLevelId != null ? !memberLevelId.equals(that.memberLevelId) : that.memberLevelId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + marketActivityId;
        result = 31 * result + (memberLevelId != null ? memberLevelId.hashCode() : 0);
        return result;
    }
}
