package com.changfa.frame.data.dto.saas;

import java.util.List;

/**
 * Created by Administrator on 2018/11/1.
 */
public class PointDetailDTO {
    private String isLong;
    private List<String > time;
    private String beginTime;
    private String endTime;
    private Integer point1;
    private Integer point2;
    private Integer point3;
    private Integer point4;
    private Integer point5;
    private Integer point6;
    private Integer point7;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsLong() {
        return isLong;
    }

    public void setIsLong(String isLong) {
        this.isLong = isLong;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public Integer getPoint1() {
        return point1;
    }

    public void setPoint1(Integer point1) {
        this.point1 = point1;
    }

    public Integer getPoint2() {
        return point2;
    }

    public void setPoint2(Integer point2) {
        this.point2 = point2;
    }

    public Integer getPoint3() {
        return point3;
    }

    public void setPoint3(Integer point3) {
        this.point3 = point3;
    }

    public Integer getPoint4() {
        return point4;
    }

    public void setPoint4(Integer point4) {
        this.point4 = point4;
    }

    public Integer getPoint5() {
        return point5;
    }

    public void setPoint5(Integer point5) {
        this.point5 = point5;
    }

    public Integer getPoint6() {
        return point6;
    }

    public void setPoint6(Integer point6) {
        this.point6 = point6;
    }

    public Integer getPoint7() {
        return point7;
    }

    public void setPoint7(Integer point7) {
        this.point7 = point7;
    }
}
