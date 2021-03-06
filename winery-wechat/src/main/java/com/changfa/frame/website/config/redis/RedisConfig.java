package com.changfa.frame.website.config.redis;

import com.changfa.frame.core.redis.RedisCache;
import com.changfa.frame.core.redis.RedisCacheManager;
import com.changfa.frame.core.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;

/**
 * redis缓存配置类
 * User: WYY
 * Date: 2019-08-11
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private   Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    //注入redisClient实例
    @Autowired
    private RedisClient redisClient;

    /**
     * 获取Jedis连接池
     */
    @Bean
    public JedisPool jedisPool() {
        logger.info("redisPoolFactory");
        return new JedisPool(jedisPoolConfig(), host, port, timeout, password);
    }

    /**
     * 获取Jedis连接池配置信息
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        logger.info("redisPoolConfig");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);    // 最大空闲连接
        jedisPoolConfig.setMinIdle(minIdle);    // 最小空闲连接
        jedisPoolConfig.setMaxTotal(maxActive); // 最大连接数
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);    // 最大阻塞时间
        // 连接耗尽时是否阻塞, false报异常, ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        return jedisPoolConfig;
    }

    @Bean
    public RedisCache redisCache() {
        RedisCache redisCache = new RedisCache();
        redisCache.setName("redisCache");
        redisCache.setCache(redisClient);
        return redisCache;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        RedisCache redisCache = redisCache();
        ArrayList<Object> cacheList = new ArrayList<>();
        cacheList.add(redisCache);
        redisCacheManager.setCaches(cacheList);
        return redisCacheManager;
    }

    /**
     * RedisTemplate配置
     * @param
     * @return
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        //定义key序列化方式
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型会出现异常信息;需要我们上面的自定义key生成策略，一般没必要
//        //定义value的序列化方式
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }

//    @Bean
//    public RedisRepositoryFactoryBean redisRepositoryFactoryBean() {
//        return null;
//    }

}
