package com.ligh.example.redisdemo.impl;

import com.ligh.example.redisdemo.IRedisHelper;
import com.ligh.example.redisdemo.IZSetTuple;
import com.ligh.example.redisdemo.config.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisHelper
 * @Description redis缓存常用接口封装
 * @Author 李光华
 * @Date 2020/12/2 14:49
 **/
@Repository
@Slf4j
public class RedisHelper implements IRedisHelper {

    /**  对redis的封装都在这个类里了   **/
    private RedisTemplate<String,Object> redisTemplate = SpringContextUtils.getBean("redisTemplate");

    private HashOperations<String,String,Object> opsForHash = SpringContextUtils.getBean("opsForHash");

    private String keyPrefix;

    public RedisHelper() {
    }

    public RedisHelper(RedisTemplate<String, Object> redisTemplate, HashOperations<String, String, Object> opsForHash, String keyPrefix) {
        this.redisTemplate = redisTemplate;
        this.opsForHash = opsForHash;
        this.keyPrefix = keyPrefix;
    }

    /**
     * 序列化
     * @param key
     * @return
     */
    private byte[] serializeString(String key) {
        RedisSerializer serializer = redisTemplate.getKeySerializer();
        return serializer.serialize(key);
    }

    private byte[] serializeObject(Object value) {
        RedisSerializer serializer = redisTemplate.getValueSerializer();
        return serializer.serialize(value);
    }

    @Override
    public String getKeyPrefix() {
        return keyPrefix;
    }

    @Override
    public boolean set(String key, Object value) {
        Boolean result = redisTemplate.execute((redisConnection) ->
                        redisConnection.set(serializeString(key), serializeObject(value))
                , true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean set(String key, Object value, long expire) {
        Boolean result = redisTemplate.execute((redisConnection) ->
                        redisConnection.set(serializeString(key), serializeObject(value), Expiration.seconds(expire)
                                , RedisStringCommands.SetOption.UPSERT)
                , true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean setIfKeyAbSent(String key, Object value) {
        Boolean result = redisTemplate.execute((redisConnection ->
                redisConnection.setNX(serializeString(key),serializeObject(value))
                ),true);
        return ValueUtil.getValue(result);
    }

    @Override
    public boolean setIfValueAbSent(String key, Object value, long expire) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key,value,expire, TimeUnit.SECONDS);
        return ValueUtil.getValue(result);
    }

    @Override
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            return ValueUtil.parse(object,clazz);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        Object object = get(key);
        if (object instanceof List){
            List<Object> list = (List<Object>) object;
            return ValueUtil.parseList(list, clazz);
        }
        return null;
    }

    @Override
    public String getString(String key) {
        Object v = get(key);
        return ValueUtil.parseString(v);
    }

    @Override
    public int getInt(String key) {
        Object v = get(key);
        return ValueUtil.parseInt(v);
    }

    @Override
    public long getLong(String key) {
        Object v = get(key);
        return ValueUtil.parseLong(v);
    }

    @Override
    public double getDouble(String key) {
        Object v = get(key);
        return ValueUtil.parseDouble(v);
    }

    @Override
    public boolean getBoolean(String key) {
        Object v = get(key);
        return ValueUtil.parseBoolean(v);
    }

    @Override
    public Object getSet(String key, Object value) {
        try {
            Object result = redisTemplate.opsForValue().getAndSet(key, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T getSet(String key, Object value, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean expire(String key, long expire) {
        return false;
    }

    @Override
    public boolean expireAt(String key, long expireTimestamp) {
        return false;
    }

    @Override
    public boolean persist(String key) {
        return false;
    }

    @Override
    public int listLen(String key) {
        return 0;
    }

    @Override
    public <T> T lindex(String key, int index, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> List<T> lrange(String key, int start, int stop, Class<T> clazz) {
        return null;
    }

    @Override
    public long lpush(String key, Object... objects) {
        return 0;
    }

    @Override
    public long rpush(String key, Object... objects) {
        return 0;
    }

    @Override
    public Object lpop(String key) {
        return null;
    }

    @Override
    public Object rpop(String key) {
        return null;
    }

    @Override
    public <T> T lpop(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T rpop(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public void ltrim(String key, int start, int stop) {

    }

    @Override
    public boolean hset(String key, String field, Object value) {
        return false;
    }

    @Override
    public boolean hsetnx(String key, String field, Object value) {
        return false;
    }

    @Override
    public Object hget(String key, String field) {
        return null;
    }

    @Override
    public <T> T hget(String key, String field, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        return false;
    }

    @Override
    public List<Object> hmget(String key, List<String> fieldList) {
        return null;
    }

    @Override
    public <T> List<T> hmget(String key, List<String> fieldList, Class<T> clazz) {
        return null;
    }

    @Override
    public Map<String, Object> hgetall(String key) {
        return null;
    }

    @Override
    public <T> Map<String, T> hmgetall(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean hexists(String key, String field) {
        return false;
    }

    @Override
    public int hdel(String key, String... fields) {
        return 0;
    }

    @Override
    public long hincrby(String key, String field, int increment) {
        return 0;
    }

    @Override
    public Set<String> hkeys(String key) {
        return null;
    }

    @Override
    public List<Object> hvals(String key) {
        return null;
    }

    @Override
    public <T> List<T> hvals(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public int hlen(String key) {
        return 0;
    }

    @Override
    public int sadd(String key, Object... members) {
        return 0;
    }

    @Override
    public int scard(String key) {
        return 0;
    }

    @Override
    public boolean sismember(String key, Object member) {
        return false;
    }

    @Override
    public Set<Object> smembers(String key) {
        return null;
    }

    @Override
    public <T> Set<T> smembers(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public Object srandmember(String key) {
        return null;
    }

    @Override
    public <T> T srandmember(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public List<Object> srandmember(String key, int count) {
        return null;
    }

    @Override
    public <T> List<T> srandmember(String key, int count, Class<T> clazz) {
        return null;
    }

    @Override
    public Object spop(String key) {
        return null;
    }

    @Override
    public <T> T spop(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public List<Object> spop(String key, int count) {
        return null;
    }

    @Override
    public <T> List<T> spop(String key, int count, Class<T> clazz) {
        return null;
    }

    @Override
    public int srem(String key, Object... members) {
        return 0;
    }

    @Override
    public boolean zadd(String key, String member, long score) {
        return false;
    }

    @Override
    public int zadd(String key, Map<String, Long> memberScoreMap) {
        return 0;
    }

    @Override
    public long zscore(String key, String member) {
        return 0;
    }

    @Override
    public int zcard(String key) {
        return 0;
    }

    @Override
    public int zcount(String key, long min, long max) {
        return 0;
    }

    @Override
    public long zincr(String key, String member) {
        return 0;
    }

    @Override
    public long zincrby(String key, String member, long increment) {
        return 0;
    }

    @Override
    public int zrank(String key, String member) {
        return 0;
    }

    @Override
    public Set<String> zrange(String key, long start, long stop) {
        return null;
    }

    @Override
    public Set<IZSetTuple> zrangeWithScores(String key, long start, long stop) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, long min, long max, boolean eqMin, boolean eqMax) {
        return null;
    }

    @Override
    public Set<IZSetTuple> zrangeByScoreWithScores(String key, long min, long max, boolean eqMin, boolean eqMax) {
        return null;
    }

    @Override
    public int zrevrank(String key, String member) {
        return 0;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long stop) {
        return null;
    }

    @Override
    public Set<IZSetTuple> zrevrangeWithScores(String key, long start, long stop) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, long min, long max, boolean eqMin, boolean eqMax) {
        return null;
    }

    @Override
    public Set<IZSetTuple> zrevrangeByScoreWithScores(String key, long min, long max, boolean eqMin, boolean eqMax) {
        return null;
    }

    @Override
    public int zrem(String key, String... members) {
        return 0;
    }

    @Override
    public boolean delByPattern(String pattern) {
        return false;
    }

    @Override
    public boolean del(String key) {
        return false;
    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public long incr(String key) {
        return 0;
    }

    @Override
    public long incrby(String key, long increment) {
        return 0;
    }

    @Override
    public long decr(String key) {
        return 0;
    }

    @Override
    public long ttl(String key) {
        return 0;
    }
}
