package doctorhoai.learn.orderservice.feign.userservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "userservice", contextId = "employeeOrder", path = "/employee", fallbackFactory = EmployeeFeignFallback.class, configuration = FeignConfig.class)
public interface EmployeeFeign {
    @PostMapping("/get/mul")
    ResponseEntity<ResponseObject> getMulEmployee(
            @RequestBody List<Integer> ids
    );
}
