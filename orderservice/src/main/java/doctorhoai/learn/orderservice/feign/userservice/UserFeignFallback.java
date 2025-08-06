package doctorhoai.learn.orderservice.feign.userservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFeignFallback implements FallbackFactory<UserFeign> {

    private final HandleFallBack handleFallBack;

    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public ResponseEntity<ResponseObject> getUserById(Integer id) {
                return handleFallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getUserByUsername(String username) {
                return handleFallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getMulUser(List<Integer> ids) {
                return handleFallBack.processFallback(cause);
            }
        };
    }
}
