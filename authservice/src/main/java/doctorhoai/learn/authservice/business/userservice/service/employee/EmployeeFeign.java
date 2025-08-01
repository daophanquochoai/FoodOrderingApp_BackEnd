package doctorhoai.learn.authservice.business.userservice.service.employee;

import doctorhoai.learn.authservice.business.userservice.model.ChangePassword;
import doctorhoai.learn.authservice.business.userservice.model.Filter;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.authservice.feign.userservice.UserFeignFallBack;
import doctorhoai.learn.authservice.feign.userservice.model.EmployeeDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(
        name = "userservice",
        path = "/employee",
        contextId = "employeeFeignBusiness",
        fallbackFactory = UserFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface EmployeeFeign {
    @PostMapping("/add")
    ResponseEntity<ResponseObject> addEmployeeIntoDb(
            @RequestBody @Valid EmployeeDto employeeDto
    );

    @GetMapping("/{username}")
    ResponseEntity<ResponseObject> getEmployeeById(
            @PathVariable String username
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateEmployee(
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeDto employeeDto
    );

    @GetMapping("/filter")
    ResponseEntity<ResponseObject> getEmployeeWithFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) Boolean isActive
    );

    @PostMapping("/all")
    ResponseEntity<ResponseObject> getListEmployeeWithFilter(
            @RequestBody Filter filter
    );

    @PutMapping("/late_login/{id}")
    ResponseEntity<ResponseObject> getLateLoginEmployee(
            @PathVariable Integer id
    );

    @PutMapping("/update/password/{username}")
    ResponseEntity<ResponseObject> updatePassword(
            @PathVariable String username,
            @RequestBody ChangePassword password
    );
}
