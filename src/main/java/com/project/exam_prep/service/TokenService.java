package com.project.exam_prep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Thêm token vào blacklist trong Redis
    public void logout(String jwtToken) {
        redisTemplate.opsForValue().set(jwtToken, "blacklisted", 1, TimeUnit.DAYS);  // Thời gian hết hạn của token
    }

    // Kiểm tra token có bị blacklist không
    public boolean isTokenBlacklisted(String jwtToken) {
        return redisTemplate.hasKey(jwtToken);
    }
}

