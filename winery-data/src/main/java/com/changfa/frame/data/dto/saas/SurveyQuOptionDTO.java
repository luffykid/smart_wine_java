package com.changfa.frame.data.dto.saas;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @Auther: yangzp
 * @Date: 2019/4/15 11:07
 */
public class SurveyQuOptionDTO {
    private Integer id;
    private Integer quId;
    private String content;
    private Integer sort;

    @Id
    @Column(name = "id", nullable = false)
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

}
