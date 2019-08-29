/*
 * WineryMasterMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-24 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineryMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WineryMasterMapper extends BaseMapper<WineryMaster, Long> {

    /**
     * 获取荣誉庄主列表
     * @return
     */
    public List<Map> selectHonourWineryList();

    /**
     * 获取荣誉庄主详情
     * @return
     */
    public Map selectHonourWineryDetail(@Param("id") Long id);
}