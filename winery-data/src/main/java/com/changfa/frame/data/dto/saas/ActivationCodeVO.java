package com.changfa.frame.data.dto.saas;

import java.io.Serializable;

public class ActivationCodeVO implements Serializable {

    private String code;
    private String phone;

    public ActivationCodeVO(String code) {
        this.code = code;
    }

    public ActivationCodeVO(String code,String phone) {
        this.code = code;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
