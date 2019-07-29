package com.changfa.frame.data.dto.saas;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/23.
 */
public class AddTheMeDTO {
    private String token;
    private Integer themeId;
    private String themeName;
    private String themeLogo;
    private String themeDescri;
    private List<Map<String,Object>> mapList;
    private String status;

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeLogo() {
        return themeLogo;
    }

    public void setThemeLogo(String themeLogo) {
        this.themeLogo = themeLogo;
    }

    public String getThemeDescri() {
        return themeDescri;
    }

    public void setThemeDescri(String themeDescri) {
        this.themeDescri = themeDescri;
    }

    public List<Map<String, Object>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, Object>> mapList) {
        this.mapList = mapList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
