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

import java.util.List;

public interface WineryActivityMapper extends BaseMapper<WineryActivity, Long> {

    /**
     * 获取未开始活动列表
     * @return
     */
    List<WineryActivity> getNoStartList();
}