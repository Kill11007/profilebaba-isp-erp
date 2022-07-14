package com.knackitsolutions.profilebaba.isperp.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtility {
  private final RedisTemplate<String, String> redisTemplate;

  public void setValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void deleteValue(String key) {
    redisTemplate.delete(key);
  }

}

