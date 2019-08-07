/*
 * MemberMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-06 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member, Long> {

}