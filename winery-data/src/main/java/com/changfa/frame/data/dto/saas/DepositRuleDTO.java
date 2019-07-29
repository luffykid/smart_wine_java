package com.changfa.frame.data.dto.saas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/18 0018.
 */
public class DepositRuleDTO implements Serializable {
    private Integer id;
    private String ruleName;
    private String token;
    private String descri;
    private List<DepositRuleItemDTO> depositRuleItemDTOS;

    private List<Map<String,Object>> checkedlist;

    public List<Map<String, Object>> getCheckedlist() {
        return checkedlist;
    }

    public void setCheckedlist(List<Map<String, Object>> checkedlist) {
        this.checkedlist = checkedlist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public List<DepositRuleItemDTO> getDepositRuleItemDTOS() {
        return depositRuleItemDTOS;
    }

    public void setDepositRuleItemDTOS(List<DepositRuleItemDTO> depositRuleItemDTOS) {
        this.depositRuleItemDTOS = depositRuleItemDTOS;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }
}
