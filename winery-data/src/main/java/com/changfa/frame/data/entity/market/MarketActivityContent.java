package com.changfa.frame.data.entity.market;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/10/15 0015.
 */
@Entity
@Table(name = "market_activity_content")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class MarketActivityContent {
    private Integer id;
    private Integer marketActivityId;
    private String content;

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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarketActivityContent that = (MarketActivityContent) o;

        if (id != that.id) return false;
        if (marketActivityId != null ? !marketActivityId.equals(that.marketActivityId) : that.marketActivityId != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (marketActivityId != null ? marketActivityId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
