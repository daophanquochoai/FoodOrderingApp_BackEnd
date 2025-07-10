package doctorhoai.learn.orderservice.redis.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveObject( String key, Object value ){
        redisTemplate.opsForValue().set(key, value, Duration.ofHours(1));
    }

    public Object getObject( String key ){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey( String key ){
        redisTemplate.delete(key);
    }

}
