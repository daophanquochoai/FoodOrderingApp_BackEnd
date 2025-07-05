package doctorhoai.learn.userservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.controller.contanst.EMessageResponse;
import doctorhoai.learn.userservice.dto.EmployeeDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.service.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addEmployeeIntoDb(
            @RequestBody @Valid EmployeeDto employeeDto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.ADD_EMPLOYEE.getMessage())
                        .data(employeeService.addEmployee(employeeDto))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEmployeeById(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_EMPLOYEE.getMessage())
                        .data(employeeService.getEmployeeById(id))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateEmployee(
            @PathVariable Integer id,
            @RequestBody @Valid EmployeeDto employeeDto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_EMPLOYEE.getMessage())
                        .data(employeeService.updateEmployee(employeeDto,id))
                        .build()
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> getEmployeeWithFilter(
            @ModelAttribute FilterUser filterUser
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_EMPLOYEE.getMessage())
                        .data(employeeService.getEmployeeByFilter(filterUser))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getListEmployeeWithFilter(
            @ModelAttribute Filter filter
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_USER.getMessage())
                        .data(employeeService.getAllEmployees(filter))
                        .build()
        );
    }
    
    @PutMapping("/late_login/{id}")
    public ResponseEntity<ResponseObject> getLateLoginEmployee(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_EMPLOYEE.getMessage())
                        .data(employeeService.updateLateLoginEmployee(id))
                        .build()
        );
    }
}
