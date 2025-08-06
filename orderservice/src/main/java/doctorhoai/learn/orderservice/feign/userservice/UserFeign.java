package doctorhoai.learn.orderservice.feign.userservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "userservice", contextId = "orderUser", path = "/user", fallbackFactory = UserFeignFallback.class, configuration = FeignConfig.class)
public interface UserFeign {
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(
            @PathVariable Integer id
    );
    @GetMapping("/username/{username}")
    ResponseEntity<ResponseObject> getUserByUsername(
            @PathVariable String username
    );
    @PostMapping("/get/mul")
    ResponseEntity<ResponseObject> getMulUser(
            @RequestBody List<Integer> ids
    );
}
