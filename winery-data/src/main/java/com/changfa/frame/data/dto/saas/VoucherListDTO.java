package com.changfa.frame.data.dto.saas;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public class VoucherListDTO implements Serializable {
    private Integer id;
    private String name;
    private String type;
    private String value;
    private String enableMoeny;//启用资金
    private String usefulTime;//有效期

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEnableMoeny() {
        return enableMoeny;
    }

    public void setEnableMoeny(String enableMoeny) {
        this.enableMoeny = enableMoeny;
    }

    public String getUsefulTime() {
        return usefulTime;
    }

    public void setUsefulTime(String usefulTime) {
        this.usefulTime = usefulTime;
    }
}
