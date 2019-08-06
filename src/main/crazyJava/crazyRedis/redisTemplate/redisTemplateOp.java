package main.crazyJava.crazyRedis.redisTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class redisTemplateOp {

    /**
     * StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
     * RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * RedisTemplate使用的序列类在在操作数据的时候，比如说存入数据会将数据先序列化成字节数组然后在存入Redis数据库，
     * 这个时候打开Redis查看的时候，你会看到你的数据不是以可读的形式展现的，而是以字节数组显示
     *
     * @param k
     * @param v
     */
    public void set(Object k, Object v) {
        this.set(k, v, 0L);
    }

    public void set(Object k, Object v, long timeOut) {
        redisTemplate.opsForValue().set(k, v, timeOut);
    }

    public void set(String k, String v) {
        this.set(k, v, 0L);
    }

    public void set(String k, String v, long timeOut) {
        stringRedisTemplate.opsForValue().set(k, v, timeOut);
    }

    public Object getObject(Object k) {
        return redisTemplate.opsForValue().get(k);
    }

    public String get(Object k) {
        return stringRedisTemplate.opsForValue().get(k);
    }

    public void putMap(String k, Map<String, Object> map) {
        stringRedisTemplate.opsForHash().putAll(k, map);
    }

    public void put(String k, String hk, String hv) {
        stringRedisTemplate.opsForHash().put(k, hk, hv);
    }

    /**
     * hk不存在时才添加
     *
     * @param k
     * @param hk
     * @param hv
     * @return
     */
    public Boolean putIfAbsent(String k, String hk, String hv) {
        return stringRedisTemplate.opsForHash().putIfAbsent(k, hk, hv);
    }


    public void deleteMap(String k, String hv) {
        //delete方法，删除key对应的hash的hashkey及其value
        redisTemplate.opsForHash().delete(k, hv);
    }

    //左添加
    public void leftPush(String k, String v) {
        stringRedisTemplate.opsForList().leftPush(k, v);
    }

    //右添加
    public void rightPush(String k, String v) {
        stringRedisTemplate.opsForList().rightPush(k, v);
    }

    /**
     * 获取指定索引位置的值, index为-1时，表示返回的是最后一个；当index大于实际的列表长度时，返回null
     *
     * @param k
     * @param index
     * @return
     */
    public String indexList(String k, int index) {
        return stringRedisTemplate.opsForList().index(k, index);
    }

    //左弹
    public String leftPop(String k) {
        return stringRedisTemplate.opsForList().leftPop(k);
    }

    //右弹
    public String rightPop(String k) {
        return stringRedisTemplate.opsForList().rightPop(k);
    }


}
