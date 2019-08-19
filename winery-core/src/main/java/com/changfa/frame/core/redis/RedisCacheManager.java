package com.changfa.frame.core.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

/**
 * 继承了 spring 内置的 AbstractCacheManager 管理 RedisCache 类缓存管理
 *
 * @author wyy
 * @date 2019-08-15 14:40
 */

public class RedisCacheManager<T extends Collection> extends AbstractCacheManager {

    private Collection<? extends RedisCache> caches;

    public void setCaches(Collection<? extends RedisCache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }
}
