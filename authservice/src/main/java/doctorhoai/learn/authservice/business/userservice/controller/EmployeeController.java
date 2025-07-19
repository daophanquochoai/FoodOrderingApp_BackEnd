package doctorhoai.learn.authservice.business.userservice.controller;

import doctorhoai.learn.authservice.business.userservice.service.employee.EmployeeFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeFeign employeeFeign;
}
