/*
 * WineryWineProdMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineryWineProd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WineryWineProdMapper extends BaseMapper<WineryWineProd, Long> {

    List<WineryWineProd> getProdListByWinerWineId(@Param("wineryWineId") Long wineryWineId);

    List<WineryWineProd> getByWineryWineById(Long id);

    int deleteWineryWineById(Long id);
}