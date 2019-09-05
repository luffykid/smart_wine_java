package com.changfa.frame.service.mybatis.common;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.core.util.SpringUtils;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *系统配置工具
 *
 * @author wyy
 * @date 2019年08月15日
 */
@Component
public final class SettingUtils {
    private static Long wineryId;

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
        return sysConfigService.get(wineryId);
    }

    /**
     * 设置酒庄ID
     */
    @Value("${system.setting.winery.id}")
    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }
}
