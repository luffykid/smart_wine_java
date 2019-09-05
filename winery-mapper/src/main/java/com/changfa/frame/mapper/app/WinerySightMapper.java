/*
 * WinerySightMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WinerySight;

import java.util.List;

public interface WinerySightMapper extends BaseMapper<WinerySight, Long> {

    WinerySight getByName(String sightName);

    List<WinerySight> selectWinerySightList(WinerySight winerySight);

    int updateSightLike(WinerySight winerySight);

    WinerySight selectWinerySightByWineryId(Long id);

    int addWinerySight(WinerySight winerySight);

    int updateWinerySight(WinerySight winerySight);
}