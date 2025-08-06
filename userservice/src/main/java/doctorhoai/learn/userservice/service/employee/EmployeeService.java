package doctorhoai.learn.userservice.service.employee;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.userservice.dto.ChangePassword;
import doctorhoai.learn.userservice.dto.EmployeeDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(EmployeeDto employeeDto, Integer id);
    EmployeeDto updateLateLoginEmployee(String username);
    EmployeeDto getEmployeeByUsername(String username);
    EmployeeDto getEmployeeByFilter(FilterUser filterUser);
    PageObject getAllEmployees(Filter employeeDto);
    void updatePassword( String username, ChangePassword password);
    List<EmployeeDto> getMulEmployee(List<Integer> ids);
}
