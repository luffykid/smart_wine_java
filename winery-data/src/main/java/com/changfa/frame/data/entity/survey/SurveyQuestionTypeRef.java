package com.changfa.frame.data.entity.survey;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:34
 */
@Entity
@Table(name = "survey_question_type_ref", schema = "winery", catalog = "")
public class SurveyQuestionTypeRef {
    private Integer id;
    private Integer typeId;
    private Integer questionId;

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
    @Column(name = "type_id", nullable = false)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "question_id", nullable = false)
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuestionTypeRef that = (SurveyQuestionTypeRef) o;
        return id == that.id &&
                typeId == that.typeId &&
                questionId == that.questionId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, typeId, questionId);
    }
}
