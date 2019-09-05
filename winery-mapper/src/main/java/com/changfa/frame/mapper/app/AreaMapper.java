/*
 * AreaMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-25 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import  com.changfa.frame.model.app.Area;

public interface AreaMapper extends BaseMapper<Area, Long> {
    //根据code 获取 Area
    Area getAreaByCode(Long code);
}