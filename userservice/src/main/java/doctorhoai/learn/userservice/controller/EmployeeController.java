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

import java.time.LocalDate;

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
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) Boolean isActive
    ){
        FilterUser filterUser = new FilterUser(id, email, phoneNumber, cccd, isActive);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_EMPLOYEE.getMessage())
                        .data(employeeService.getEmployeeByFilter(filterUser))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getListEmployeeWithFilter(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        Filter filter = Filter.builder()
                .search(search)
                .isActive(isActive)
                .startDate(startDate)
                .endDate(endDate)
                .build();
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
