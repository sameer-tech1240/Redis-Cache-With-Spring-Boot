package com.learn.redis.cache.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    /* ttl -> time to live -. means -> For how many times the data should be store in Redis Cache */

    public void setValueByKey(String key, Object object, Long ttl) throws JsonProcessingException {
        String valueToBeSaved = objectMapper.writeValueAsString(object);
        log.info("Value to be saved in Redis Cache {} ", valueToBeSaved);
        redisTemplate.opsForValue().set(key, valueToBeSaved, ttl, TimeUnit.MINUTES);
    }

    public <T> T getValueByKey(String key, Class<T> type) {
        try {
            Object value = redisTemplate.opsForValue().get(key);

            return value != null ? objectMapper.readValue(value.toString(), type) : null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Error while fetching value from Redis Cache {} ", e.getMessage());
            return null;
        }
    }

    public boolean deleteRecordFromRedisCache(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Long deleteMultiRecordFromRedisCache(List<String> keys) {

        Long delete = redisTemplate.delete(keys);
        return delete == null ? 0 : delete;
    }


}
