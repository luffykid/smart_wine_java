package com.changfa.frame.data.dto.saas;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 11:07
 */
public class SurveyQuestionnaireDTO {
    private Integer id;
    private String surveyName;
    private String surveyNote;
    private Integer wineryId;
    private Integer userId;
    private Date beginTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private Integer modifier;
    private List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList;
    private String isRelease; //是否发布 Y:发布，N未发布

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

    public String getSurveyNote() {
        return surveyNote;
    }

    public void setSurveyNote(String surveyNote) {
        this.surveyNote = surveyNote;
    }

    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public List<SurveyQuestionTypeDTO> getSurveyQuestionTypeDTOList() {
        return surveyQuestionTypeDTOList;
    }

    public void setSurveyQuestionTypeDTOList(List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList) {
        this.surveyQuestionTypeDTOList = surveyQuestionTypeDTOList;
    }

    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
    }
}
