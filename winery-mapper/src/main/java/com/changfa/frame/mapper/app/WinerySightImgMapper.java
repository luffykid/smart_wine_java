/*
 * WinerySightImgMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-07 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WinerySightImg;

import java.util.List;

public interface WinerySightImgMapper extends BaseMapper<WinerySightImg, Long> {

    List<String> findScenicImgById(Long id);

    int deleteWinerySightImgById(Long id);
}