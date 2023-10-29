package com.study.mall.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author peng
 * @version 1.0
 * @description redis工具类
 * @date 2023/10/23 20:13
 */
@Component
public class RedisCommonProcessor {
    //依赖注入我们定义的 redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过key获取value
     * @param key
     * @return
     */
    public Object get(String key) {
        if (key == null) {
            throw new UnsupportedOperationException("Redis key could not be null!");
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 向redis中存入key: value
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 向reids中存入key: value 数据对，并支持过期时间
     * @param key
     * @param value
     * @param time
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    public void remove(String orderId) {
        redisTemplate.delete(orderId);
    }
}
