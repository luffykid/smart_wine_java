package com.changfa.frame.data.dto.operate;

import com.changfa.frame.data.dto.saas.SurveyQuestionTypeDTO;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 11:07
 */
public class SurveyQuestionnaireListDTO {
    private Integer id;
    private String surveyName;
    private String isRelease; //是否发布 Y:发布，N未发布
    private Date createTime;
    private Integer answerNum;//测评人数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }
}
