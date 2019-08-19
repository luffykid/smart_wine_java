package com.changfa.frame.service.mybatis.app;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.model.app.SystemConfig;
import com.changfa.frame.service.mybatis.common.BaseService;

/**
 * 系统设置service
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
public interface SystemConfigService extends BaseService<SystemConfig, Long> {

    /**
     * 查询系统配置对象数据
     *
     * @return
     */
    Setting get();

    /**
     * 将系统配置对象数据加入Redis缓存
     *
     * @param settingTemp 系统配置对象
     */
    void set(Setting settingTemp);
}
