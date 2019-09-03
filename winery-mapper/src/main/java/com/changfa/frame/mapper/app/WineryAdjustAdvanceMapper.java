/*
 * WineryAdjustAdvanceMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineryAdjustAdvance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WineryAdjustAdvanceMapper extends BaseMapper<WineryAdjustAdvance, Long> {


    List<WineryAdjustAdvance> selectListByAdjustIdAndStatusOrderBySort(@Param("adjustId") Long adjustId,@Param("status") Integer status);
}