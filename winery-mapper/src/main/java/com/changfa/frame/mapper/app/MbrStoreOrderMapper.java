/*
 * MbrStoreOrderMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-23 Created
 */
package com.changfa.frame.mapper.app;


import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.MbrStoreOrder;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MbrStoreOrderMapper extends BaseMapper<MbrStoreOrder, Long> {

    /**
     * 我的储酒
     * @param mbrId
     * @return
     */
    public List<Map> selectStoreList(@Param("mbrId") Long mbrId);
}