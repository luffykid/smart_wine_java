package com.changfa.frame.data.dto.wechat;

import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */
public class ThemeDetailDTO {
    private Integer id;
    private List<String> logo;
    private String name;
    private String ather;
    private String context;
    private List<NewProdListDTO> newProdLists;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getLogo() {
        return logo;
    }

    public void setLogo(List<String> logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAther() {
        return ather;
    }

    public void setAther(String ather) {
        this.ather = ather;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<NewProdListDTO> getNewProdLists() {
        return newProdLists;
    }

    public void setNewProdLists(List<NewProdListDTO> newProdLists) {
        this.newProdLists = newProdLists;
    }
}
