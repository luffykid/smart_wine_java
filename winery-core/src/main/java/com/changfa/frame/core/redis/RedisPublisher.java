package com.changfa.frame.core.redis;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * redis发布者
 *  * @author wyy
 *  * @date 2019-08-15 14:40
 */
public class RedisPublisher {

    private RedisClient redisClient;

    private Jedis jedis;

    private String channel;

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     * 发布字符串类型消息
     *
     * @param message 消息
     */
    public void publish(String message) {
        if (StringUtils.isNotBlank(message)) {
            this.publish(message.getBytes());
        }
    }

    /**
     * 发布字节类型消息
     *
     * @param message 消息
     */
    public void publish(byte[] message) {
        if (ArrayUtils.isEmpty(message)) {
            return;
        }
        try {
            getJedis().publish(channel.getBytes(), message);
        } catch (Exception e) {
            synchronized (this) {
                if(this.jedis.isConnected()){
                    redisClient.releaseJedisInstance(this.jedis);
                }
                this.jedis = null;
            }
        }
    }

    /**
     * 发布者启动
     */
    public Jedis getJedis() {
        if (jedis == null) {
            synchronized (this) {
                if (jedis == null) {
                    jedis = redisClient.getJedis();
                }
            }
        }
        return jedis;
    }

    /**
     * 销毁jedis
     */
    public void destroy() {
        redisClient.releaseJedisInstance(this.jedis);
    }

}
