/*
 * WineCustomAdvanceMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineCustomAdvance;

import java.util.List;

public interface WineCustomAdvanceMapper extends BaseMapper<WineCustomAdvance, Long> {

    List<WineCustomAdvance> getWineCustomAdvanceList(List<Long> wineCustomElementIdList);
}