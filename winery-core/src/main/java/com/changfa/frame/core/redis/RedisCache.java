package com.changfa.frame.core.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 根据spring API自定义一个缓存，实现缓存类
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */
public class RedisCache implements Cache {

    /**
     * 默认保存5小时
     */
    private static final int EXPIRE = 18000;

    /**
     * 缓存属性名称
     */
    private String name;

    /**
     * redis客户端
     */
    public RedisClient cache;


    public RedisCache() {
    }

    public RedisCache(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RedisClient getCache() {
        return cache;
    }

    public void setCache(RedisClient cache) {
        this.cache = cache;
    }

    /**
     * 清空所有的缓存
     */
    public void clear() {
        cache.flushAll();
    }

    @Override
    public void evict(Object key) {
        cache.del(key);
    }

    /**
     * 根据Key值获得缓存数据
     */
    public ValueWrapper get(Object key) {
        ValueWrapper result = null;
        Object thevalue = cache.get(key);
        if (thevalue != null) {
            result = new SimpleValueWrapper(thevalue);
        }
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return cache;
    }

    /**
     * 添加缓存
     */
    public void put(Object key, Object value) {
        cache.setAndExpire(key, value, EXPIRE);
    }

    @Override
    public <T> T get(Object o, Class<T> type) {
        Object object = cache.get(o);
        if (object != null && type != null && type.isInstance(o)) {
            return (T) object;
        }
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        ValueWrapper valueWrapper = get(key);
        if (valueWrapper == null) {
            cache.setAndExpire(key, value, EXPIRE);
        }
        return valueWrapper;
    }

}
