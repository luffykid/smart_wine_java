package com.changfa.frame.data.entity.survey;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 10:34
 */
@Entity
@Table(name = "survey_answer", schema = "winery", catalog = "")
public class SurveyAnswer {
    private Integer id;
    private Integer surveyId;
    private Integer answererId;
    private String answererName;
    private String companyName;
    private String phone;
    private Date bgAnTime;
    private Date endAnTime;
    private Integer sort;
    private Date createTime;
    private String  winerySuggestion; //酒旗星建议
    private String answererMessage;//答题者留言反馈
    private Integer score;//答题者获得的总分


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
    @Column(name = "answerer_id", nullable = false)
    public Integer getAnswererId() {
        return answererId;
    }

    public void setAnswererId(Integer answererId) {
        this.answererId = answererId;
    }

    @Basic
    @Column(name = "answerer_name", nullable = false)
    public String getAnswererName() {
        return answererName;
    }

    public void setAnswererName(String answererName) {
        this.answererName = answererName;
    }

    @Basic
    @Column(name = "company_name", nullable = false)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "bg_an_time", nullable = true)
    public Date getBgAnTime() {
        return bgAnTime;
    }

    public void setBgAnTime(Date bgAnTime) {
        this.bgAnTime = bgAnTime;
    }

    @Basic
    @Column(name = "end_an_time", nullable = true)
    public Date getEndAnTime() {
        return endAnTime;
    }

    public void setEndAnTime(Date endAnTime) {
        this.endAnTime = endAnTime;
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
    @Column(name = "winery_suggestion", nullable = false)
    public String getWinerySuggestion() {
        return winerySuggestion;
    }

    public void setWinerySuggestion(String winerySuggestion) {
        this.winerySuggestion = winerySuggestion;
    }

    @Basic
    @Column(name = "answerer_message", nullable = false)
    public String getAnswererMessage() {
        return answererMessage;
    }

    public void setAnswererMessage(String answererMessage) {
        this.answererMessage = answererMessage;
    }

    @Basic
    @Column(name = "score", nullable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyAnswer that = (SurveyAnswer) o;
        return id == that.id &&
                surveyId == that.surveyId &&
                answererId == that.answererId &&
                sort == that.sort &&
                Objects.equals(bgAnTime, that.bgAnTime) &&
                Objects.equals(endAnTime, that.endAnTime) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(winerySuggestion, that.winerySuggestion) &&
                Objects.equals(answererMessage, that.answererMessage)&&
                Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, surveyId, answererId, bgAnTime, endAnTime, sort, createTime,winerySuggestion,answererMessage,score);
    }
}
