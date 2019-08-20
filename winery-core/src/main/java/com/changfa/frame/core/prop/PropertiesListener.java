package com.changfa.frame.core.prop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动监听：加载指定属性文件
 *
 * @author wyy
 * @date 2019-08-15 01:39
 */
@Component
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent> {

    /**
     * 属性文件集合
     */
    @Value("${system.properties.files}")
    private List<String> propFileNameList;

    /**
     * 事件监听
     *
     * @param applicationStartedEvent
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        PropConfig.loadAllProperties(propFileNameList);
    }
}
