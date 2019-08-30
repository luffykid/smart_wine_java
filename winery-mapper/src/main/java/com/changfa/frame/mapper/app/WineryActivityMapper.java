/*
 * WineryActivityMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineryActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WineryActivityMapper extends BaseMapper<WineryActivity, Long> {

    /**
     * 获取未结束活动列表
     * @return
     */
    List<WineryActivity> selectNoEndList(@Param("mbrId") Long mbrId);

    /**
     * 获取酒庄活动列表
     * @return
     */
    List<WineryActivity> selectSecList(@Param("mbrId") Long mbrId);

    /**
     * 获取酒庄活动详细
     * @param id
     * @param mbrId
     * @return
     */
    WineryActivity selectSecById(@Param("id") Long id, @Param("mbrId") Long mbrId);

    /**
     * 我参加的活动列表
     * @param mbrId
     * @return
     */
    List<WineryActivity> selectMySignAct(@Param("mbrId") Long mbrId);
}