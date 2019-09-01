package com.changfa.frame.service.mybatis.app.impl;

import com.changfa.frame.core.setting.Setting;
import com.changfa.frame.core.util.DateUtil;
import com.changfa.frame.mapper.core.SystemConfigMapper;
import com.changfa.frame.model.app.SystemConfig;
import com.changfa.frame.model.app.Winery;
import com.changfa.frame.service.mybatis.app.SystemConfigService;
import com.changfa.frame.service.mybatis.common.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 系统设置service实现
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
@Service("systemConfigServiceImpl")
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, Long> implements SystemConfigService {

    private static final long serialVersionUID = -980253557544742081L;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    /**
     * setting
     */
    private static Setting setting;


    /**
     * 获取系统设置
     *
     * @return 系统设置
     */
    @Cacheable(value = "redisCache", key = "T(com.changfa.frame.core.redis.RedisConsts).SYSTEM_SETTING")
    public Setting get(Long wineryId) {
        if (setting == null) {
            synchronized (this) {
                if (setting == null) {
                    reload(wineryId);
                }
            }
        }
        return setting;
    }

    /**
     * 设置系统设置
     *
     * @param settingTemp 系统设置
     */
    @CacheEvict(value = "redisCache", key = "T(com.changfa.frame.core.redis.RedisConsts).SYSTEM_SETTING")
    public void set(Setting settingTemp,Long wineryId) {
        Date now = new Date();
        try {
            Field[] fields = Setting.class.getDeclaredFields();//setting属性
            List<SystemConfig> sysConfigs = new ArrayList<>();
            for (Field field : fields) {
                SystemConfig sysConfig = new SystemConfig();
                sysConfig.setWineryId(wineryId);
                sysConfig.setConfigName(field.getName());
                PropertyDescriptor pd;
                try {
                    pd = new PropertyDescriptor(field.getName(), Setting.class);
                } catch (IntrospectionException e) {
                    continue;
                }
                Method readMethod = pd.getReadMethod();//获得get方法
                Object value = readMethod.invoke(settingTemp);//获得值
                if (value != null) {
                    value = value.toString();
                }
                sysConfig.setConfigValue((String) value);
                sysConfig.setModifyDate(now);
                sysConfigs.add(sysConfig);
            }
            systemConfigMapper.updates(sysConfigs);//存入数据库
            synchronized (SystemConfigServiceImpl.class) {
                setting = null;//清空setting对象
            }
        } catch (Exception e) {
            log.error("更新系统设置参数出错:" + ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 给setting对象设置值
     */
    private void reload(Long wineryId) {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setWineryId(wineryId);
        List<SystemConfig> sysConfigs = selectList(systemConfig);
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        Map<String, Method> methodMap = new HashMap<String, Method>();
        setting = new Setting();
        try {
            Field[] fields = Setting.class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();//属性名
                fieldMap.put(fieldName.toLowerCase(), field);
                PropertyDescriptor pd;
                try {
                    pd = new PropertyDescriptor(field.getName(), Setting.class);
                } catch (IntrospectionException e) {
                    continue;
                }
                Method writeMethod = pd.getWriteMethod();//获取set方法
                methodMap.put(fieldName.toLowerCase(), writeMethod);
            }

            for (SystemConfig sysConfig : sysConfigs) {//数据库setting的name、value对象
                String name = sysConfig.getConfigName();//setting对应属性名
                String value = sysConfig.getConfigValue();//setting对应属性值
                if (value == null) {
                    continue;
                }
                value = value.trim();
                name = name.trim();
                Field field = fieldMap.get(name.toLowerCase());
                if (field == null) {
                    continue;
                }
                Method method = methodMap.get(name.toLowerCase());
                if (method == null) {
                    continue;
                }
                fill(setting, field, method, value);//给setting各个属性赋值
            }

        } catch (Exception e) {
            log.error("获取系统设置参数出错：" + ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 将字符串值转换为合适的值填充到对象的属性
     *
     * @param bean  被填充的java bean
     * @param field 需要填充的属性
     * @param value 字符串值
     */
    private void fill(Object bean, Field field, Method method, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        try {
            Object[] objects = new Object[1];

            String type = field.getType().getName();//属性数据类型

            if ("java.lang.String".equals(type)) {
                objects[0] = value;
            } else if ("java.lang.Integer".equals(type)) {
                if (value.length() > 0) objects[0] = Integer.valueOf(value);
            } else if ("java.lang.Float".equals(type)) {
                if (value.length() > 0) objects[0] = Float.valueOf(value);
            } else if ("java.lang.Double".equals(type)) {
                if (value.length() > 0) objects[0] = Double.valueOf(value);
            } else if ("java.math.BigDecimal".equals(type)) {
                if (value.length() > 0) objects[0] = new BigDecimal(value);
            } else if ("java.util.Date".equals(type)) {
                if (value.length() > 0) objects[0] = DateUtil.convertStrToCalendar(value, "yyyy-MM-dd HH:mm:ss");
            } else if ("java.lang.Boolean".equals(type)) {
                if (value.length() > 0) objects[0] = Boolean.valueOf(value);
            } else if ("java.lang.Long".equals(type)) {
                if (value.length() > 0) objects[0] = Long.valueOf(value);
            }
            method.invoke(bean, objects);
        } catch (Exception e) {
            log.error("转换setting变量类型出错：" + ExceptionUtils.getFullStackTrace(e));
        }
    }
}
