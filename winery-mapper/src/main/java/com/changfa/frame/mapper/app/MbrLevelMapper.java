/*
 * MbrLevelMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-21 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrLevel;

import java.util.List;

public interface MbrLevelMapper extends BaseMapper<MbrLevel, Long> {

    List<MbrLevel> getMbrLevelList();

}