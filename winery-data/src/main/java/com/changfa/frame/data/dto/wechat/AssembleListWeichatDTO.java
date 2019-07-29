package com.changfa.frame.data.dto.wechat;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/5/9.
 */

public class AssembleListWeichatDTO {
    private Integer id;//拼团列表表id
    private List<AssembleUserWeichatListDTO> userList;
    private Date endTime;//最终活动结束的时间点
    private Integer assemblePreson;//团队差多少人数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<AssembleUserWeichatListDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<AssembleUserWeichatListDTO> userList) {
        this.userList = userList;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getAssemblePreson() {
        return assemblePreson;
    }

    public void setAssemblePreson(Integer assemblePreson) {
        this.assemblePreson = assemblePreson;
    }
}
