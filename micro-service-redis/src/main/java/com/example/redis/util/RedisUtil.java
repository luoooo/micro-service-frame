package com.example.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Set key-value
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Set key-value with expiration
     *
     * @param key      key
     * @param value    value
     * @param timeout  timeout
     * @param timeUnit time unit
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Get value by key
     *
     * @param key key
     * @return value
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Delete key
     *
     * @param key key
     * @return true if deleted
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * Delete multiple keys
     *
     * @param keys keys
     * @return number of deleted keys
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * Set expiration
     *
     * @param key      key
     * @param timeout  timeout
     * @param timeUnit time unit
     * @return true if set
     */
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * Get expiration
     *
     * @param key key
     * @return expiration in seconds
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Check if key exists
     *
     * @param key key
     * @return true if exists
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Increment value
     *
     * @param key   key
     * @param delta increment
     * @return new value
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Decrement value
     *
     * @param key   key
     * @param delta decrement
     * @return new value
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * Get hash value
     *
     * @param key     key
     * @param hashKey hash key
     * @return hash value
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * Set hash value
     *
     * @param key     key
     * @param hashKey hash key
     * @param value   value
     */
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * Set hash value with expiration
     *
     * @param key      key
     * @param hashKey  hash key
     * @param value    value
     * @param timeout  timeout
     * @param timeUnit time unit
     */
    public void hSet(String key, String hashKey, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * Get all hash entries
     *
     * @param key key
     * @return hash entries
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Set multiple hash entries
     *
     * @param key key
     * @param map entries
     */
    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * Set multiple hash entries with expiration
     *
     * @param key      key
     * @param map      entries
     * @param timeout  timeout
     * @param timeUnit time unit
     */
    public void hSetAll(String key, Map<String, Object> map, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * Delete hash entries
     *
     * @param key      key
     * @param hashKeys hash keys
     * @return number of deleted entries
     */
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * Check if hash key exists
     *
     * @param key     key
     * @param hashKey hash key
     * @return true if exists
     */
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Increment hash value
     *
     * @param key     key
     * @param hashKey hash key
     * @param delta   increment
     * @return new value
     */
    public Long hIncrement(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * Decrement hash value
     *
     * @param key     key
     * @param hashKey hash key
     * @param delta   decrement
     * @return new value
     */
    public Long hDecrement(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * Get set members
     *
     * @param key key
     * @return set members
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Check if value is member of set
     *
     * @param key   key
     * @param value value
     * @return true if member
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Add set members
     *
     * @param key    key
     * @param values values
     * @return number of added members
     */
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Add set members with expiration
     *
     * @param key      key
     * @param timeout  timeout
     * @param timeUnit time unit
     * @param values   values
     * @return number of added members
     */
    public Long sAdd(String key, long timeout, TimeUnit timeUnit, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        redisTemplate.expire(key, timeout, timeUnit);
        return count;
    }

    /**
     * Get set size
     *
     * @param key key
     * @return set size
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Remove set members
     *
     * @param key    key
     * @param values values
     * @return number of removed members
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * Get list range
     *
     * @param key   key
     * @param start start index
     * @param end   end index
     * @return list range
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Get list size
     *
     * @param key key
     * @return list size
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * Get list element by index
     *
     * @param key   key
     * @param index index
     * @return element
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * Set list element by index
     *
     * @param key   key
     * @param index index
     * @param value value
     */
    public void lSet(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * Remove list elements
     *
     * @param key   key
     * @param count count
     * @param value value
     * @return number of removed elements
     */
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
} 