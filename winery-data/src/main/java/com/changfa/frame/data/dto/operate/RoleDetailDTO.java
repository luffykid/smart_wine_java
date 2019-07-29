package com.changfa.frame.data.dto.operate;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/25.
 */

public class RoleDetailDTO {
    private Integer id;
    private String wineryName; //所属酒庄
    private String name;
    private String descri;
    private String status;
    private List<Map<String, String>> menuList; //菜单权限配置列表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWineryName() {
        return wineryName;
    }

    public void setWineryName(String wineryName) {
        this.wineryName = wineryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Map<String, String>> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Map<String, String>> menuList) {
        this.menuList = menuList;
    }
}
