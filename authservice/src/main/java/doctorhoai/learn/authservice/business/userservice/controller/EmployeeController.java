package doctorhoai.learn.authservice.business.userservice.controller;

import doctorhoai.learn.authservice.business.userservice.model.ChangePassword;
import doctorhoai.learn.authservice.business.userservice.model.Filter;
import doctorhoai.learn.authservice.business.userservice.service.employee.EmployeeFeign;
import doctorhoai.learn.authservice.feign.userservice.model.EmployeeDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeFeign employeeFeign;

    @GetMapping("/{username}")
    public ResponseEntity<ResponseObject> getEmployeeById(
            @PathVariable String username
    ){
        return employeeFeign.getEmployeeById(username);
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> getListEmployeeWithFilter(
            @RequestBody Filter filter
    ){
        return employeeFeign.getListEmployeeWithFilter(filter);
    }

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addEmployeeIntoDb(
            @RequestBody @Valid EmployeeDto employeeDto
    ){
        return employeeFeign.addEmployeeIntoDb(employeeDto);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateEmployee(
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeDto employeeDto
    ){
        return employeeFeign.updateEmployee(id, employeeDto);
    }

    @PutMapping("/update/password/{username}")
    public ResponseEntity<ResponseObject> updatePassword(
            @PathVariable String username,
            @RequestBody ChangePassword password
    ){
        return employeeFeign.updatePassword(username, password);
    }
}
