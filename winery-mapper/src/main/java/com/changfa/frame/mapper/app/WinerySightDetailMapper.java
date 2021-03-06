/*
 * WinerySightDetailMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WinerySightDetail;

import java.util.List;

public interface WinerySightDetailMapper extends BaseMapper<WinerySightDetail, Long> {

    List<WinerySightDetail> findSightImageTextByWinerySightId(Long id);

}