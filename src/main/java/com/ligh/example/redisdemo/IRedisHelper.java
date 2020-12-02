package com.ligh.example.redisdemo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis常用接口
 */
public interface IRedisHelper {

    /**
     * key统一前缀
     * @return
     */
    String getKeyPrefix();

    /**
     * 设置缓存（无过期时间）
     * @param key
     * @param value
     * @return
     */
    boolean set(String key,Object value);

    /**
     * 设置缓存（有过期时间）
     * @param key
     * @param value
     * @param expire 过期时间，单位 秒
     * @return
     */
    boolean set(String key,Object value,long expire);

    /**
     * 只有在key不存在时，设置key的值
     * @param key
     * @param value
     * @return
     */
    boolean setIfKeyAbSent(String key,Object value);

    /**
     * value 不存在时set
     * @param key
     * @param value
     * @param expire 过期时间，单位 秒
     * @return
     */
    boolean setIfValueAbSent(String key,Object value,long expire);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取缓存对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key,Class<T> clazz);

    /**
     * 获取缓存，读取为list形式
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getList(String key,Class<T> clazz);

    /**
     * 获取String缓存
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 获取int型缓存
     * @param key
     * @return
     */
    int getInt(String key);

    /**
     * 获取long型缓存
     * @param key
     * @return
     */
    long getLong(String key);

    /**
     * 获取double缓存
     * @param key
     * @return
     */
    double getDouble(String key);

    /**
     * 获取boolean型缓存
     * @param key
     * @return
     */
    boolean getBoolean(String key);

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值
     * @param key
     * @param value
     * @return
     */
    Object getSet(String key, Object value);

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值
     * @param key
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getSet(String key, Object value, Class<T> clazz);

    /**
     * 设置缓存过期时间
     * @param key
     * @param expire 秒
     * @return
     */
    boolean expire(String key,long expire);

    /**
     * 设置缓存过期时间点
     * @param key
     * @param expireTimestamp 过期的时间戳 10位数字 单位：秒
     * @return
     */
    boolean expireAt(String key,long expireTimestamp);

    /**
     * 移除缓存的过期时间，让key永久保存
     * @param key
     * @return
     */
    boolean persist(String key);

    /**
     * 获取列表长度
     * @param key
     * @return
     */
    int listLen(String key);

    /**
     * 通过索引获取列表中的元素，0表示第一个位置
     * @param key
     * @param index
     * @param clazz
     * @return
     */
    <T> T lindex(String key, int index, Class<T> clazz);

    /**
     * 获取列表指定范围内的元素，0表示第一个位置，负数表示倒数位置
     *   如-1表示倒数第一个位置，-2表示倒数第二个位置
     * @param key
     * @param start
     * @param stop
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> lrange(String key,int start,int stop,Class<T> clazz);

    /**
     * 在列表头部添加一个或多个值
     * @param key
     * @param objects
     * @return 返回列表的长度
     */
    long lpush(String key,Object... objects);

    /**
     * 在列表尾部添加一个或多个值
     * @param key
     * @param objects
     * @return 返回列表的长度
     */
    long rpush(String key, Object... objects);

    /**
     * 移出并获取列表的第一个元素
     * @param key
     * @return
     */
    Object lpop(String key);

    /**
     * 移除并获取列表的最后一个元素
     * @param key
     * @return
     */
    Object rpop(String key);

    /**
     * 移出并获取列表的第一个元素
     * @param key
     * @param clazz
     * @return
     */
    <T> T lpop(String key, Class<T> clazz);

    /**
     * 移除并获取列表的最后一个元素
     * @param key
     * @param clazz
     * @return
     */
    <T> T rpop(String key, Class<T> clazz);

    /**
     * 对一个列表进行修剪，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * @param key
     * @param start
     * @param stop
     */
    void ltrim(String key, int start, int stop);

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     * @param key
     * @param field
     * @param value
     * @return
     */
    boolean hset(String key, String field, Object value);

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值
     * @param key
     * @param field
     * @param value
     * @return
     */
    boolean hsetnx(String key, String field, Object value);

    /**
     * 获取存储在哈希表中指定字段的值
     * @param key
     * @param field
     * @return
     */
    Object hget(String key, String field);

    /**
     * 获取存储在哈希表中指定字段的值，并把值设置为
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T hget(String key, String field, Class<T> clazz);

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param key
     * @param map
     * @return
     */
    boolean hmset(String key, Map<String, Object> map);

    /**
     * 获取所有给定字段的值，返回List<Object>类型
     * @param key
     * @param fieldList
     * @return
     */
    List<Object> hmget(String key, List<String> fieldList);

    /**
     * 获取所有给定字段的值，返回List<T>类型
     * @param key
     * @param fieldList
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> hmget(String key, List<String> fieldList, Class<T> clazz);

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * @param key
     * @return
     */
    Map<String, Object> hgetall(String key);

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Map<String, T> hmgetall(String key, Class<T> clazz);

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     * @param key
     * @param field
     * @return
     */
    boolean hexists(String key, String field);

    /**
     * 删除一个或多个哈希表字段
     * @param key
     * @param fields
     * @return
     */
    int hdel(String key, String... fields);

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     * @param key
     * @param field
     * @param increment
     * @return
     */
    long hincrby(String key, String field, int increment);

    /**
     * 获取所有哈希表中的字段
     * @param key
     * @return
     */
    Set<String> hkeys(String key);

    /**
     * 获取哈希表中所有值
     * @param key
     * @return
     */
    List<Object> hvals(String key);

    /**
     * 获取哈希表中所有值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> hvals(String key, Class<T> clazz);

    /**
     * 获取哈希表中字段的数量
     * @param key
     * @return
     */
    int hlen(String key);

    /**
     * 向集合添加一个或多个成员
     * @param key
     * @param members
     * @return
     */
    int sadd(String key, Object... members);

    /**
     * 获取集合的成员数
     * @param key
     * @return
     */
    int scard(String key);

    /**
     * 判断 member 元素是否是集合 key 的成员
     * @param key
     * @param member
     * @return
     */
    boolean sismember(String key, Object member);

    /**
     * 返回集合中的所有成员
     * @param key
     * @return
     */
    Set<Object> smembers(String key);

    /**
     * 返回集合中的所有成员
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Set<T> smembers(String key, Class<T> clazz);

    /**
     * 返回集合中一个随机数
     * @param key
     * @return
     */
    Object srandmember(String key);

    /**
     * 返回集合中一个随机数
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T srandmember(String key, Class<T> clazz);

    /**
     * 返回集合中多个随机数
     * @param key
     * @param count
     * @return
     */
    List<Object> srandmember(String key, int count);

    /**
     * 返回集合中多个随机数
     * @param key
     * @param count
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> srandmember(String key, int count, Class<T> clazz);

    /**
     * 移除并返回集合中的一个随机元素
     * @param key
     * @return
     */
    Object spop(String key);

    /**
     * 移除并返回集合中的一个随机元素
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T spop(String key, Class<T> clazz);

    /**
     * 移除并返回集合中的多个随机元素
     * @param key
     * @param count
     * @return
     */
    List<Object> spop(String key, int count);

    /**
     * 移除并返回集合中的多个随机元素
     * @param key
     * @param count
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> spop(String key, int count, Class<T> clazz);

    /**
     * 移除集合中一个或多个成员
     * @param key
     * @param members
     * @return 被成功移除的元素的数量，不包括被忽略的元素
     */
    int srem(String key, Object... members);

    /**
     * 向有序集合添加一个成员，或者更新已存在成员的分数
     * 推荐使用long型的分数，如果为double型的分数，也尽量转为long型分数
     * double型数据储存在redis中会损失精度
     * @param key
     * @param member
     * @param score
     * @return
     */
    boolean zadd(String key, String member, long score);

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     * 推荐使用long型的分数，如果为double型的分数，也尽量转为long型分数
     * double型数据储存在redis中会损失精度
     * @param key
     * @param memberScoreMap
     * @return
     */
    int zadd(String key, Map<String, Long> memberScoreMap);

    /**
     * 返回有序集中，成员的分数值
     * 推荐使用long型的分数，如果为double型的分数，也尽量转为long型分数
     * double型数据储存在redis中会损失精度
     * @param key
     * @param member
     * @return
     */
    long zscore(String key, String member);

    /**
     * 获取有序集合的成员数
     * @param key
     * @return
     */
    int zcard(String key);

    /**
     * 计算在有序集合中指定区间分数的成员数
     * @param key
     * @param min
     * @param max
     * @return
     */
    int zcount(String key, long min, long max);

    /**
     * 有序集合中对指定成员的分数加上1
     * @param key
     * @param member
     * @return
     */
    long zincr(String key, String member);

    /**
     * 有序集合中对指定成员的分数加上增量 increment
     * @param key
     * @param member
     * @param increment
     * @return
     */
    long zincrby(String key, String member, long increment);

    /**
     * 返回正序有序集合中指定成员的排名
     * @param key
     * @param member
     * @return
     */
    int zrank(String key, String member);

    /**
     * 返回正序有序集中，指定排名区间内的成员列表
     * @param key
     * @param start
     * @param stop
     * @return
     */
    Set<String> zrange(String key, long start, long stop);

    /**
     * 返回正序有序集中，指定排名区间内的成员、成绩map
     * @param key
     * @param start
     * @param stop
     * @return
     */
    Set<IZSetTuple> zrangeWithScores(String key, long start, long stop);

    /**
     * 返回正序有序集合中指定分数区间的成员列表
     * @param key
     * @param min 最小分数
     * @param max 最大分数
     * @param eqMin 为true时，表示>=eqMin，否则表示>eqMin
     * @param eqMax 为true时，表示<=eqMax，否则表示>eqMax
     * @return
     */
    Set<String> zrangeByScore(String key, long min, long max, boolean eqMin, boolean eqMax);

    /**
     * 返回正序有序集合中指定分数区间的成员、分数map
     * @param key
     * @param min 最小分数
     * @param max 最大分数
     * @param eqMin 为true时，表示>=eqMin，否则表示>eqMin
     * @param eqMax 为true时，表示<=eqMax，否则表示>eqMax
     * @return
     */
    Set<IZSetTuple> zrangeByScoreWithScores(String key, long min, long max, boolean eqMin, boolean eqMax);

    /**
     * 返回倒序有序集合中指定成员的排名
     * @param key
     * @param member
     * @return
     */
    int zrevrank(String key, String member);

    /**
     * 返回倒序有序集中，指定排名区间内的成员列表
     * @param key
     * @param start
     * @param stop
     * @return
     */
    Set<String> zrevrange(String key, long start, long stop);

    /**
     * 返回倒序有序集中，指定排名区间内的成员、成绩map
     * @param key
     * @param start
     * @param stop
     * @return
     */
    Set<IZSetTuple> zrevrangeWithScores(String key, long start, long stop);


    /**
     * 返回倒序有序集合中指定分数区间的成员列表
     * @param key
     * @param min 最小分数
     * @param max 最大分数
     * @param eqMin 为true时，表示>=eqMin，否则表示>eqMin
     * @param eqMax 为true时，表示<=eqMax，否则表示>eqMax
     * @return
     */
    Set<String> zrevrangeByScore(String key, long min, long max, boolean eqMin, boolean eqMax);

    /**
     * 返回倒序有序集合中指定分数区间的成员、分数map
     * @param key
     * @param min 最小分数
     * @param max 最大分数
     * @param eqMin 为true时，表示>=eqMin，否则表示>eqMin
     * @param eqMax 为true时，表示<=eqMax，否则表示>eqMax
     * @return
     */
    Set<IZSetTuple> zrevrangeByScoreWithScores(String key, long min, long max, boolean eqMin, boolean eqMax);

    /**
     * 移除有序集合中的一个或多个成员，返回成员、成绩map
     * @param key
     * @param members
     * @return
     */
    int zrem(String key, String... members);

    /**
     * 根据正则表达式删除缓存
     * @param pattern
     */
    boolean delByPattern(String pattern);

    /**
     * 根据KEY移除缓存
     * @param key
     */
    boolean del(String key);

    /**
     * 判断缓存key是否存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 缓存值加1
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * 将 key 所储存的值加上给定的增量值increment
     * @param key
     * @param increment 增量
     * @return
     */
    long incrby(String key, long increment);

    /**
     * 缓存值减1
     * @param key
     * @return
     */
    long decr(String key);

    /**
     * 获取缓存的过期时间，单位：秒
     * @param key
     * @return 返回过期时间，-1为没有过期时间，-2表示key不存在
     */
    long ttl(String key);

}
