/*
 * SystemConfigMapper.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-15 Created
 */
package com.changfa.frame.mapper.core;

import com.changfa.frame.mapper.common.BaseMapper;
import com.changfa.frame.model.app.SystemConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemConfigMapper extends BaseMapper<SystemConfig, Long> {

    /**
     * 更新系统设置
     *
     * @param list 系统设置对象集合
     */
    void updates(@Param("list") List<SystemConfig> list);
}