package doctorhoai.learn.authservice.jwt.service.token;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    // luu giu token voi thoi gian ton tai 10h
    @Override
    public void saveToken(String token, String username) {
        redisTemplate.opsForValue().set(token, username, Duration.ofHours(10));
    }

    // tim token xem co khong
    @Override
    public boolean findToken(String token) {
        String returnValue = redisTemplate.opsForValue().get(token);
        if( returnValue == null ) {
            return false;
        }
        return true;
    }

    // xoa token khi token dang xuat
    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}

