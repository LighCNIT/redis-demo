package com.ligh.example.redisdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private IRedisHelper redisHelper;

    @Autowired
    private ILock redisLock;

    @Test
    public void testSet(){
        redisTemplate.opsForValue().set("bb","root");
        System.out.println(redisTemplate.opsForValue().get("bb"));
        System.out.println(redisHelper.smembers("bb"));
    }

    @Test
    public void testList(){
        System.out.println(redisHelper.lpush("cc",1,2,3,4,5));
        System.out.println(redisHelper.rpush("cc",5,6,7,8));
    }

    @Test
    public void testHash(){
        redisHelper.hset("dd","xie","开心");
        System.out.println(redisHelper.hget("dd","xie"));
    }

    @Test
    public void testLock(){
        boolean lock = redisLock.tryLock("aa","aa",0);
        if (lock){
            System.out.println("执行任务");
        }else {
            System.out.println("没有抢到锁");
        }
        redisLock.unLock("aa","aa");
        boolean lock1 = redisLock.tryLock("aa","aa",0);
        if (lock1){
            System.out.println("获取到锁了");
        }else {
            System.out.println("没有获取到锁");
        }
    }


}
