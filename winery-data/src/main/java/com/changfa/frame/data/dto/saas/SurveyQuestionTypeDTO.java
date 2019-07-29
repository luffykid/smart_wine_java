package com.changfa.frame.data.dto.saas;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 11:07
 */
public class SurveyQuestionTypeDTO {
    private Integer id;
    private String typeName;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private List<SurveyQuestionDTO> surveyQuestionDTOList;
    private Integer typeScore;

    @Id
    @Column(name = "id", nullable = false)
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

    public List<SurveyQuestionDTO> getSurveyQuestionDTOList() {
        return surveyQuestionDTOList;
    }

    public void setSurveyQuestionDTOList(List<SurveyQuestionDTO> surveyQuestionDTOList) {
        this.surveyQuestionDTOList = surveyQuestionDTOList;
    }

    public Integer getTypeScore() {
        return typeScore;
    }

    public void setTypeScore(Integer typeScore) {
        this.typeScore = typeScore;
    }
}
