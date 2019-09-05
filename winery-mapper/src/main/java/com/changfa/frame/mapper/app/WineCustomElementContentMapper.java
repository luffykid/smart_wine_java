/*
 * WineCustomElementContentMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineCustomElementContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WineCustomElementContentMapper extends BaseMapper<WineCustomElementContent, Long> {

    List<WineCustomElementContent> getListByWineCustomId(Long id);

    List<Long> getIdListByWineCustomId(Long id);

    int deleteById(List<Long> wineCustomElementContentId);

    int saveList(@Param("list") List<WineCustomElementContent> wineCustomElementContentList);

    int deleteByWineCustomId(Long id);
}