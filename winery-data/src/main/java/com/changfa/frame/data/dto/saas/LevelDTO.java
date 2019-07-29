package com.changfa.frame.data.dto.saas;

/**
 * Created by Administrator on 2018/10/24.
 */
public class LevelDTO {
    private String name;
    private Integer upgradeExperience;
    private Integer experience;
    private String wwc;

    public String getWwc() {
        return wwc;
    }

    public void setWwc(String wwc) {
        this.wwc = wwc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUpgradeExperience() {
        return upgradeExperience;
    }

    public void setUpgradeExperience(Integer upgradeExperience) {
        this.upgradeExperience = upgradeExperience;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
