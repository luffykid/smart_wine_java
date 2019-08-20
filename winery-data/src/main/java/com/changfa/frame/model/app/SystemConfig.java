/*
 * SystemConfig.java
 * Copyright(C) 北京畅发科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-08-15 Created
 */
package com.changfa.frame.model.app;

import com.alibaba.fastjson.annotation.JSONField;
import com.changfa.frame.model.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 系统配置表
 *
 * @version 1.0 2019-08-15
 */
public class SystemConfig extends BaseEntity {

    private static final long serialVersionUID = 441790072660951040L;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 获取配置名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置配置名称
     */
    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    /**
     * 获取配置值
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * 设置配置值
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }
}