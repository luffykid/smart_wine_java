package com.changfa.frame.data.dto.wechat;

import java.io.Serializable;
import java.util.List;

public class UserMemberLevelDTO implements Serializable {

    private String userLevelName;
    private String userExperience;
    private String usefulTime;
    private List<MemberLevelDTO> memberLevelDTOList;
    private String userLevelContent1;
    private String userLevelContent2;
    private String userLevelContent3;

    public String getUserLevelName() {
        return userLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        this.userLevelName = userLevelName;
    }

    public String getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(String userExperience) {
        this.userExperience = userExperience;
    }

    public String getUsefulTime() {
        return usefulTime;
    }

    public void setUsefulTime(String usefulTime) {
        this.usefulTime = usefulTime;
    }

    public List<MemberLevelDTO> getMemberLevelDTOList() {
        return memberLevelDTOList;
    }

    public void setMemberLevelDTOList(List<MemberLevelDTO> memberLevelDTOList) {
        this.memberLevelDTOList = memberLevelDTOList;
    }

    public String getUserLevelContent1() {
        return userLevelContent1;
    }

    public void setUserLevelContent1(String userLevelContent1) {
        this.userLevelContent1 = userLevelContent1;
    }

    public String getUserLevelContent2() {
        return userLevelContent2;
    }

    public void setUserLevelContent2(String userLevelContent2) {
        this.userLevelContent2 = userLevelContent2;
    }

    public String getUserLevelContent3() {
        return userLevelContent3;
    }

    public void setUserLevelContent3(String userLevelContent3) {
        this.userLevelContent3 = userLevelContent3;
    }
}
