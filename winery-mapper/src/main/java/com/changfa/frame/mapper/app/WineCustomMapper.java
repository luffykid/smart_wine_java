/*
 * WineCustomMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-26 Created
 */
package com.changfa.frame.mapper.app;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.WineCustom;

import java.util.List;

public interface WineCustomMapper extends BaseMapper<WineCustom, Long> {

    List<WineCustom> getWineCustomList();

    List<WineCustom> getWineCustomListByName(String customName);

    WineCustom getWineCustomContianProd(Long id);
}