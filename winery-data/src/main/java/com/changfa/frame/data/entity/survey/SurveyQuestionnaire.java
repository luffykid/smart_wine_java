package com.changfa.frame.data.entity.survey;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:14
 */
@Entity
@Table(name = "survey_questionnaire", schema = "winery", catalog = "")
public class SurveyQuestionnaire {
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
    private String isRelease; //是否发布 Y:发布，N未发布
    private Integer isDelete; //是否删除 1存在，0删除

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
    @Column(name = "survey_name", nullable = false, length = 50)
    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    @Basic
    @Column(name = "survey_note", nullable = true, length = -1)
    public String getSurveyNote() {
        return surveyNote;
    }

    public void setSurveyNote(String surveyNote) {
        this.surveyNote = surveyNote;
    }

    @Basic
    @Column(name = "winery_id", nullable = true)
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "begin_time", nullable = true)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    @Basic
    @Column(name = "modifier", nullable = true)
    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "is_release", nullable = true)
    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
    }

    @Basic
    @Column(name = "is_delete", nullable = true)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyQuestionnaire that = (SurveyQuestionnaire) o;
        return id == that.id &&
                Objects.equals(surveyName, that.surveyName) &&
                Objects.equals(surveyNote, that.surveyNote) &&
                Objects.equals(wineryId, that.wineryId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(beginTime, that.beginTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(modifier, that.modifier)&&
                Objects.equals(isRelease, that.isRelease)&&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surveyName, surveyNote, wineryId, userId, beginTime, endTime, createTime, updateTime, modifier,isRelease,isDelete);
    }
}
