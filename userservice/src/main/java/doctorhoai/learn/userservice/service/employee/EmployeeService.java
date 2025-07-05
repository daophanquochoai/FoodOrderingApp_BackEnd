package doctorhoai.learn.userservice.service.employee;

import doctorhoai.learn.userservice.dto.EmployeeDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(EmployeeDto employeeDto, Integer id);
    EmployeeDto updateLateLoginEmployee(Integer id);
    EmployeeDto getEmployeeById(Integer id);
    EmployeeDto getEmployeeByFilter(FilterUser filterUser);
    List<EmployeeDto> getAllEmployees(Filter employeeDto);
}
