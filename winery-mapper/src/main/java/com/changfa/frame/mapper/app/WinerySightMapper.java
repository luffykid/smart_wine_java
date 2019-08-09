/*
 * WinerySightMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.WinerySight;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface WinerySightMapper extends BaseMapper<WinerySight, Long> {

    List<WinerySight> queryById(Long id);
}