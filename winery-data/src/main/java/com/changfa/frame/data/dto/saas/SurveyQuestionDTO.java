package com.changfa.frame.data.dto.saas;

import com.changfa.frame.data.entity.survey.SurveyQuOption;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 11:07
 */
public class SurveyQuestionDTO {
    private Integer id;
    private String quTitle;
    private Integer sort;
    private Date createTime;
    private List<SurveyQuOption> surveyQuOptionList;
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuTitle() {
        return quTitle;
    }

    public void setQuTitle(String quTitle) {
        this.quTitle = quTitle;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<SurveyQuOption> getSurveyQuOptionList() {
        return surveyQuOptionList;
    }

    public void setSurveyQuOptionList(List<SurveyQuOption> surveyQuOptionList) {
        this.surveyQuOptionList = surveyQuOptionList;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
