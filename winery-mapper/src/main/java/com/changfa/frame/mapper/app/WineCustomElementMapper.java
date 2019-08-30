/*
 * WineCustomElementMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineCustomElement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WineCustomElementMapper extends BaseMapper<WineCustomElement, Long> {

    String getElementNameById(Long wineCustomElementId);

    String getElementNameByWineCustomId(Long id);

    List<WineCustomElement> getwineCustomElementList();

    List<WineCustomElement> getwineCustomElementListByid(@Param("listId") List<Long> wineCustomElementId);

    List<WineCustomElement> getCustomElementListByWineCustomId(Long id);

}