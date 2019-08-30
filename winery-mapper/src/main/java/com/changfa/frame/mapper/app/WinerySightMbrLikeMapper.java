/*
 * WinerySightMbrLikeMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-28 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WinerySightMbrLike;

public interface WinerySightMbrLikeMapper extends BaseMapper<WinerySightMbrLike, Long> {

    WinerySightMbrLike getByWinerySightId(Long id, Long userid);

    int updateLikeStatusById(Long id, int status);
}