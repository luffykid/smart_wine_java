package com.changfa.frame.data.entity.survey;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:34
 */
@Entity
@Table(name = "survey_an_option", schema = "winery")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveyAnOption {
    private Integer id;
    private Integer belongSurveyId;
    private Integer belongAnswerId;
    private Integer quId;
    private Integer quOptionId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "belong_survey_id", nullable = false)
    public Integer getBelongSurveyId() {
        return belongSurveyId;
    }

    public void setBelongSurveyId(Integer belongSurveyId) {
        this.belongSurveyId = belongSurveyId;
    }

    @Basic
    @Column(name = "belong_answer_id", nullable = false)
    public Integer getBelongAnswerId() {
        return belongAnswerId;
    }

    public void setBelongAnswerId(Integer belongAnswerId) {
        this.belongAnswerId = belongAnswerId;
    }

    @Basic
    @Column(name = "qu_id", nullable = false)
    public Integer getQuId() {
        return quId;
    }

    public void setQuId(Integer quId) {
        this.quId = quId;
    }

    @Basic
    @Column(name = "qu_option_id", nullable = false)
    public Integer getQuOptionId() {
        return quOptionId;
    }

    public void setQuOptionId(Integer quOptionId) {
        this.quOptionId = quOptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyAnOption that = (SurveyAnOption) o;
        return id == that.id &&
                belongSurveyId == that.belongSurveyId &&
                belongAnswerId == that.belongAnswerId &&
                quId == that.quId &&
                quOptionId == that.quOptionId;
    }

}
