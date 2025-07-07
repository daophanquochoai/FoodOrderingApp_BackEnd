package doctorhoai.learn.foodservice.feign.userservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.foodservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeignFallBack implements FallbackFactory<UserFeign> {

    private final HandleFallBack fallBack;
    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public ResponseEntity<ResponseObject> getUserById(Integer id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
