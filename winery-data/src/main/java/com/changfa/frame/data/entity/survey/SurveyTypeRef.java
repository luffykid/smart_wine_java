package com.changfa.frame.data.entity.survey;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:34
 */
@Entity
@Table(name = "survey_type_ref", schema = "winery", catalog = "")
public class SurveyTypeRef {
    private Integer id;
    private Integer surveyId;
    private Integer typeId;

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
    @Column(name = "survey_id", nullable = false)
    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    @Basic
    @Column(name = "type_id", nullable = false)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyTypeRef that = (SurveyTypeRef) o;
        return id == that.id &&
                surveyId == that.surveyId &&
                typeId == that.typeId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, surveyId, typeId);
    }
}
