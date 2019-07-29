package com.changfa.frame.data.dto.saas;

/**
 * @Auther: yangzp
 * @Date: 2019/4/17 10:21
 */
public class AnOptionDTO {
    private int id;
    private int belongSurveyId;
    private int belongAnswerId;
    private int quId;
    private int quOptionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBelongSurveyId() {
        return belongSurveyId;
    }

    public void setBelongSurveyId(int belongSurveyId) {
        this.belongSurveyId = belongSurveyId;
    }

    public int getBelongAnswerId() {
        return belongAnswerId;
    }

    public void setBelongAnswerId(int belongAnswerId) {
        this.belongAnswerId = belongAnswerId;
    }

    public int getQuId() {
        return quId;
    }

    public void setQuId(int quId) {
        this.quId = quId;
    }

    public int getQuOptionId() {
        return quOptionId;
    }

    public void setQuOptionId(int quOptionId) {
        this.quOptionId = quOptionId;
    }
}
