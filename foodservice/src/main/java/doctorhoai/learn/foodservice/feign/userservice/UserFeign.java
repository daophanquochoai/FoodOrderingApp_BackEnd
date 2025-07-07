package doctorhoai.learn.foodservice.feign.userservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice", path = "/user", contextId = "foodUser", fallbackFactory = UserFeignFallBack.class)
public interface UserFeign {
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(
            @PathVariable Integer id
    );
}
