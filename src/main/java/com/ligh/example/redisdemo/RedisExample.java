package com.ligh.example.redisdemo;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @ClassName RedisExample
 * @Description
 * @Author 李光华
 * @Date 2020/11/30 10:03
 **/

public class RedisExample {

    @Resource
    private RedisTemplate redisTemplate;

    public void redisSet(String key){
        redisTemplate.opsForValue().set(key,"ligh");
    }

    public Object redisGet(String key){
        return  redisTemplate.opsForValue().get(key);
    }

    public static void main(String[] args) {
        RedisExample redisExample = new RedisExample();
        redisExample.redisSet("ligh");
        System.out.println(redisExample.redisGet("ligh"));
    }
}
