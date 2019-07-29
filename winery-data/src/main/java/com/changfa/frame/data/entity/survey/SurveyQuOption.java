package com.changfa.frame.data.entity.survey;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:34
 */
@Entity
@Table(name = "survey_qu_option", schema = "winery", catalog = "")
public class SurveyQuOption {
    private Integer id;
    private Integer quId;
    private String content;
    private Integer sort;

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
    @Column(name = "qu_id", nullable = false)
    public Integer getQuId() {
        return quId;
    }

    public void setQuId(Integer quId) {
        this.quId = quId;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuOption that = (SurveyQuOption) o;
        return id == that.id &&
                quId == that.quId &&
                sort == that.sort &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, quId, content, sort);
    }
}
