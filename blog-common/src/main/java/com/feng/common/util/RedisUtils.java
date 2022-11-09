package com.feng.common.util;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Mr.Feng
 * @date 7/21/2022 10:44 AM
 */
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Boolean del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decr(String key, long delta) {
        return null;
    }

    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Boolean hSet(String key, String hashKey, Object value, long time) {
        return null;
    }

    public void hSet(String key, String hashKey, Object value) {

    }

    public Map hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        return null;
    }

    public void hSetAll(String key, Map<String, ?> map) {

    }

    public void hDel(String key, Object... hashKey) {

    }

    public Boolean hHasKey(String key, String hashKey) {
        return null;
    }

    public Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public Long hDecr(String key, String hashKey, Long delta) {
        return null;
    }

    public Double zIncr(String key, Object value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public Double zDecr(String key, Object value, Double score) {
        return null;
    }

    public Map<Object, Double> zReverseRangeWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end)
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Map<Object, Double> zAllScore(String key) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeWithScores(key, 0, -1))
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    public Set<Object> sMembers(String key) {
        return null;
    }

    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public Long sAddExpire(String key, long time, Object... values) {
        return null;
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Long sRemove(String key, Object... values) {
        return null;
    }

    public List<Object> lRange(String key, long start, long end) {
        return null;
    }

    public Long lSize(String key) {
        return null;
    }

    public Object lIndex(String key, long index) {
        return null;
    }

    public Long lPush(String key, Object value) {
        return null;
    }

    public Long lPush(String key, Object value, long time) {
        return null;
    }

    public Long lPushAll(String key, Object... values) {
        return null;
    }

    public Long lPushAll(String key, Long time, Object... values) {
        return null;
    }

    public Long lRemove(String key, long count, Object value) {
        return null;
    }

    public Boolean bitAdd(String key, int offset, boolean b) {
        return null;
    }

    public Boolean bitGet(String key, int offset) {
        return null;
    }

    
    public Long bitCount(String key) {
        return null;
    }

    public List<Long> bitField(String key, int limit, int offset) {
        return null;
    }

    
    public byte[] bitGetAll(String key) {
        return new byte[0];
    }

    public Long hyperAdd(String key, Object... value) {
        return null;
    }

    public Long hyperGet(String... key) {
        return null;
    }

    public void hyperDel(String key) {

    }

    public Long geoAdd(String key, Double x, Double y, String name) {
        return null;
    }

    public List<Point> geoGetPointList(String key, Object... place) {
        return null;
    }

    public Distance geoCalculationDistance(String key, String placeOne, String placeTow) {
        return null;
    }

    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoNearByPlace(String key, String place, Distance distance, long limit, Sort.Direction sort) {
        return null;
    }

    public List<String> geoGetHash(String key, String... place) {
        return null;
    }
}
