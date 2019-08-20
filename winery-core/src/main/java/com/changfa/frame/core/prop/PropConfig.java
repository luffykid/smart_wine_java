package com.changfa.frame.core.prop;

import org.springframework.beans.BeansException;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 用于保存项目properties配置文件属性
 *
 * @author WYY
 */
public class PropConfig {

    public static Map<String, String> propertiesMap = new HashMap<>();


    /**
     * 加载属性配置文件中的所有属性
     *
     * @param propFileNameList 属性文件名称集合
     */
    public static void loadAllProperties(List<String> propFileNameList) {
        try {
            for (String propertyFileName : propFileNameList) {
                Properties properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
                processProperties(properties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装属性
     *
     * @param props 属性对象
     * @throws BeansException
     */
    private static void processProperties(Properties props) throws BeansException {
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                propertiesMap.put(keyStr, new String(props.getProperty(keyStr).getBytes("utf-8"), "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据属性名获取属性值
     *
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        return String.valueOf(propertiesMap.get(name));
    }

    /**
     * 获取所有属性值
     *
     * @return
     */
    public static Map<String, String> getAllProperty() {
        return propertiesMap;
    }
}
