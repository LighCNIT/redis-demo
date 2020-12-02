package com.ligh.example.redisdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName RedisTest
 * @Description 一个简单的redis测试
 * @Author 李光华
 * @Date 2020/11/30 10:11
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testSet(){
        redisTemplate.opsForValue().set("bb","root");
        System.out.println(redisTemplate.opsForValue().get("bb"));
    }
}
