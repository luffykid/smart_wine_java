package com.changfa.frame.data.entity.survey;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:34
 */
@Entity
@Table(name = "survey_question_type", schema = "winery", catalog = "")
public class SurveyQuestionType {
    private Integer id;
    private String typeName;
    private Integer sort;
    private Date createTime;
    private Date updateTime;

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
    @Column(name = "type_name", nullable = true, length = 50)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuestionType that = (SurveyQuestionType) o;
        return id == that.id &&
                sort == that.sort &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, typeName, sort, createTime, updateTime);
    }
}
