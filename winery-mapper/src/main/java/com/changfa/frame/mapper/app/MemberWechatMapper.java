package com.changfa.frame.mapper.app;


import com.changfa.frame.model.MemberWechat;

public interface MemberWechatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberWechat record);

    int insertSelective(MemberWechat record);

    MemberWechat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberWechat record);

    int updateByPrimaryKey(MemberWechat record);
}