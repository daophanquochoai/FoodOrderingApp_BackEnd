package doctorhoai.learn.authservice.feign.userservice;

import doctorhoai.learn.authservice.feign.userservice.model.EmployeeDto;
import doctorhoai.learn.authservice.feign.userservice.model.Filter.Filter;
import doctorhoai.learn.authservice.feign.userservice.model.Filter.FilterUser;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "userservice", contextId = "authEmployee", path = "/employee", fallback = EmployeeFeignFallBack.class)
public interface EmployeeFeign {
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addEmployeeIntoDb(
            @RequestBody @Valid EmployeeDto employeeDto
    );

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEmployeeById(
            @PathVariable Integer id
    );

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateEmployee(
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeDto employeeDto
    );

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> getEmployeeWithFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) Boolean isActive
    );

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getListEmployeeWithFilter(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    );

    @PutMapping("/late_login/{id}")
    public ResponseEntity<ResponseObject> getLateLoginEmployee(
            @PathVariable Integer id
    );
}
