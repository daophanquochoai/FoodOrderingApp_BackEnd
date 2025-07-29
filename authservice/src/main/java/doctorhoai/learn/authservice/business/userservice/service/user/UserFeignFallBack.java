package doctorhoai.learn.authservice.business.userservice.service.user;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.authservice.feign.userservice.model.UserDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserFeignFallBack implements FallbackFactory<UserFeign> {

    private final HandleFallBack fallBack;

    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public ResponseEntity<ResponseObject> addUserIntoDb(UserDto userDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getUserById(Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateUser(Integer id, UserDto userDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getUserWithFilter(Integer id, String email, String phoneNumber, String cccd, Boolean isActive) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getListUserWithFilter(String search, Boolean isActive, LocalDate startDate, LocalDate endDate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateLatestUpdateTime(Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getUserByUsername(String username) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
