/*
 * WineCellarActivityMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-27 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineCellarActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WineCellarActivityMapper extends BaseMapper<WineCellarActivity, Long> {

    /**
     * 获取酒庄活动列表
     * @return
     */
    List<WineCellarActivity> selectSecList(@Param("mbrId") Long mbrId);

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    WineCellarActivity selectSecById(@Param("id") Long id, @Param("mbrId") Long mbrId);

    /**
     * 获取关联景点的酒庄活动列表
     * @return
     */

    List<WineCellarActivity> selectListByWinerySightId(@Param("winerySightId") Long winerySightId);

}