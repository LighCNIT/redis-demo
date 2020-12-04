package com.ligh.example.redisdemo.impl;

import com.ligh.example.redisdemo.ILock;
import com.ligh.example.redisdemo.IRedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName RedisLock
 * @Description redis 分布式锁
 * @Author 李光华
 * @Date 2020/12/4 16:06
 **/
@Service
@Slf4j
public class RedisLock implements ILock {

    @Autowired
    private IRedisHelper redisHelper;

    @Override
    public boolean tryLock(String key, String salt, long maxWaitSeconds) {
        AtomicLong sleepSeconds = new AtomicLong(0L);
        long expire = maxWaitSeconds == 0 ? MAX_HOLD_SECONDS : maxWaitSeconds;
        boolean ret = redisHelper.setIfValueAbSent(key, salt, expire);
        //不管有多少个线程竞争,最终只会返回给一个为true
        while (!ret && maxWaitSeconds > 0) {
            ret = redisHelper.setIfValueAbSent(key, salt, expire);
            if (!ret && (maxWaitSeconds != sleepSeconds.get())) {
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    //不处理
                }
                sleepSeconds.getAndIncrement();
            }
        }
        return ret;
    }

    @Override
    public boolean unLock(String key, String saltValue) {
        Object object = redisHelper.get(key);
        if (object == null) {
            //表示没加锁
            return true;
        }
        String salt = (String) object;
        if (!salt.equals(saltValue)) {
            //不是同一个请求加的锁
            return false;
        } else {
            redisHelper.del(key);
            return false;
        }
    }
}
