package com.ligh.example.redisdemo;

/**
 * 分布式锁接口
 */
public interface ILock {

    /**
     * 持有锁最大时间，300秒
     */
    long MAX_HOLD_SECONDS = 300L;

    /**
     * 获取锁
     * @param key
     * @param salt 盐值，锁内容，有一种情况：如果获取锁之前尝试解锁，如果不加salt，则可以直接解锁，可以传任何字符串
     * @param maxWaitSeconds 最大等待时间 单位（秒）
     * @return
     */
    boolean tryLock(String key,String salt,long maxWaitSeconds);

    /**
     * 解锁
     * @param key
     * @param saltValue
     * @return
     */
    boolean unLock(String key,String saltValue);
}
