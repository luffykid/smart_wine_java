package com.changfa.frame.data.dto.saas;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yangzp
 * @Date: 2019/4/17 10:20
 */
public class AnswerDTO {
    private int id;
    private int surveyId;
    private Integer answererId;
    private String answererName;
    private String companyName;
    private String phone;
    private Date bgAnTime;
    private Date endAnTime;
    private int sort;
    private Date createTime;
    private List<AnOptionDTO> anOptionDTOList;
    private Integer score;
    private List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList;
    private String  winerySuggestion; //酒旗星建议
    private String answererMessage;//答题者留言反馈

    public String getWinerySuggestion() {
        return winerySuggestion;
    }

    public void setWinerySuggestion(String winerySuggestion) {
        this.winerySuggestion = winerySuggestion;
    }

    public String getAnswererMessage() {
        return answererMessage;
    }

    public void setAnswererMessage(String answererMessage) {
        this.answererMessage = answererMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getAnswererId() {
        return answererId;
    }

    public void setAnswererId(Integer answererId) {
        this.answererId = answererId;
    }

    public String getAnswererName() {
        return answererName;
    }

    public void setAnswererName(String answererName) {
        this.answererName = answererName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBgAnTime() {
        return bgAnTime;
    }

    public void setBgAnTime(Date bgAnTime) {
        this.bgAnTime = bgAnTime;
    }

    public Date getEndAnTime() {
        return endAnTime;
    }

    public void setEndAnTime(Date endAnTime) {
        this.endAnTime = endAnTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<AnOptionDTO> getAnOptionDTOList() {
        return anOptionDTOList;
    }

    public void setAnOptionDTOList(List<AnOptionDTO> anOptionDTOList) {
        this.anOptionDTOList = anOptionDTOList;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<SurveyQuestionTypeDTO> getSurveyQuestionTypeDTOList() {
        return surveyQuestionTypeDTOList;
    }

    public void setSurveyQuestionTypeDTOList(List<SurveyQuestionTypeDTO> surveyQuestionTypeDTOList) {
        this.surveyQuestionTypeDTOList = surveyQuestionTypeDTOList;
    }
}
