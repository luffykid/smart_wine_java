package com.changfa.frame.website.utils;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.core.util.SpringUtils;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import org.springframework.util.Assert;

public final class SettingUtils {

    /**
     * 不可实例化
     */
    private SettingUtils() {
    }

    /**
     * 获取系统设置
     *
     * @return 系统设置
     */
    public static Setting get() {
        Object sysConfigServiceObj = SpringUtils.getBean("systemConfigServiceImpl");
        Assert.notNull(sysConfigServiceObj, "sysConfigService为空");
        SystemConfigService sysConfigService = (SystemConfigService) sysConfigServiceObj;
        return sysConfigService.get();
    }
}
