/*
 * WineryWineMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineryWine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WineryWineMapper extends BaseMapper<WineryWine, Long> {

    List<WineryWine> getWineryWineList();

    List<WineryWine> getWineryWineListByName(@Param("name") String name, @Param("status") Integer status);

    int updateWineryWineStatus(@Param("id") Long id, @Param("status") Integer status);
}