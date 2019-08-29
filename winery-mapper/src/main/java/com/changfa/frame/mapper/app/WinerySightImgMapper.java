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
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WinerySightImgMapper extends BaseMapper<WinerySightImg, Long> {

    List<WinerySightImg> findScenicImgById(Long id);

    int deleteWinerySightImgById(Long id);

    List<WinerySightImg> findListByWinerySightId(Long id);

    int deleteByWinerySightId(Long id);

    int saveList(@Param("list") List<WinerySightImg> winerySightImgList);
}