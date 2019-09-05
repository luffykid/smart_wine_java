/*
 * WinCustomElementAdvanceMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WinCustomElementAdvance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WinCustomElementAdvanceMapper extends BaseMapper<WinCustomElementAdvance, Long> {

    List<WinCustomElementAdvance> getByWineCustomElementContentId(Long wineCustomElementContentId);

    int saveList(@Param("winCustomElementAdvanceList") List<WinCustomElementAdvance> winCustomElementAdvanceList);

    int deleteByWineCustomElementContentIdList(@Param("list") List<Long> wineCustomElementContentIdList);
}