package com.changfa.frame.data.dto.saas;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/29.
 */
public class PointRewardRuleItemDTO {
    private Integer point;
    private List<Integer> voucherId;
    private String mesone;
    private String mestwo;
    private List<Map<String,Object>> meslist;

    public String getMesone() {
        return mesone;
    }

    public void setMesone(String mesone) {
        this.mesone = mesone;
    }

    public String getMestwo() {
        return mestwo;
    }

    public void setMestwo(String mestwo) {
        this.mestwo = mestwo;
    }

    public List<Map<String, Object>> getMeslist() {
        return meslist;
    }

    public void setMeslist(List<Map<String, Object>> meslist) {
        this.meslist = meslist;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public List<Integer> getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(List<Integer> voucherId) {
        this.voucherId = voucherId;
    }
}
