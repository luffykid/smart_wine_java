package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;

public class MemberLevelDTO implements Serializable {

    private String name;
    private String upgradeExperience;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpgradeExperience() {
        return upgradeExperience;
    }

    public void setUpgradeExperience(String upgradeExperience) {
        this.upgradeExperience = upgradeExperience;
    }
}
