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
import org.apache.ibatis.annotations.Param;

public interface WinerySightMbrLikeMapper extends BaseMapper<WinerySightMbrLike, Long> {

    WinerySightMbrLike getByWinerySightId(@Param("id") Long id,@Param("userid")  Long userid);

    int updateLikeStatusById(Long id, int status);
}