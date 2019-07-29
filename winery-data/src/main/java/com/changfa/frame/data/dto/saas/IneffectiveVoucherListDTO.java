package com.changfa.frame.data.dto.saas;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public class IneffectiveVoucherListDTO implements Serializable {
    private Integer id;
    private String name;
    private String type;
    private String usefulTime;//有效期
    private String ineffective;//归档日期
    private String ineffectiveUserName;//归档人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsefulTime() {
        return usefulTime;
    }

    public void setUsefulTime(String usefulTime) {
        this.usefulTime = usefulTime;
    }

    public String getIneffective() {
        return ineffective;
    }

    public void setIneffective(String ineffective) {
        this.ineffective = ineffective;
    }

    public String getIneffectiveUserName() {
        return ineffectiveUserName;
    }

    public void setIneffectiveUserName(String ineffectiveUserName) {
        this.ineffectiveUserName = ineffectiveUserName;
    }
}
