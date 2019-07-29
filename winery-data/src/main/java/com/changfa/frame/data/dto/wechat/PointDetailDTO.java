package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.util.Date;

public class PointDetailDTO implements Serializable {

    private Integer userPointDetailId;
    private String status;
    private Date date;
    private Integer pointChange;

    public Integer getUserPointDetailId() {
        return userPointDetailId;
    }

    public void setUserPointDetailId(Integer userPointDetailId) {
        this.userPointDetailId = userPointDetailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPointChange() {
        return pointChange;
    }

    public void setPointChange(Integer pointChange) {
        this.pointChange = pointChange;
    }
}
