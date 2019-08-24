/*
 * MemberMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-06 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.Member;

import java.util.List;
import java.util.Map;

/**
 * 会员Mapper
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
public interface MemberMapper extends BaseMapper<Member, Long> {

    /**
     * 根据手机号查询会员
     *
     * @param phone 手机号
     * @return
     */
    Member selectByPhone(String phone);

    /**
     * 获取招募会员列表
     *
     * @param mbrId
     * @return
     */
    List<Map<String, Object>> selectSubList(Long mbrId);

    /**
     * 获取招募会员数据统计
     *
     * @param mbrId
     * @return
     */
    List<Map<String, Object>> selectSubStatis(Long mbrId);

    /**
     * 根据openId查询会员数据
     *
     * @param openId 会员微信标识
     * @return
     */
    Member selectByOpenId(String openId);
}