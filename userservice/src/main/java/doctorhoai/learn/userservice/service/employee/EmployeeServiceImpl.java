package doctorhoai.learn.userservice.service.employee;

import doctorhoai.learn.userservice.dto.EmployeeDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.exception.exception.CCCDDuplicate;
import doctorhoai.learn.userservice.exception.exception.EmailDuplicate;
import doctorhoai.learn.userservice.exception.exception.EmployeeNotFound;
import doctorhoai.learn.userservice.exception.exception.RoleNotFound;
import doctorhoai.learn.userservice.model.Employee;
import doctorhoai.learn.userservice.model.Role;
import doctorhoai.learn.userservice.repository.EmployeeRepository;
import doctorhoai.learn.userservice.repository.RoleRepository;
import doctorhoai.learn.userservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Mapper mapper;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        checkInfoEmployee(employeeDto);
        Role role = roleRepository.findByRoleName(employeeDto.getRole().getRoleName()).orElseThrow(RoleNotFound::new);

        Employee employee = mapper.convertToEmployee(employeeDto);
        employee.setRole(role);
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Integer id) {
        checkInfoEmployee(employeeDto);
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFound::new);
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setIsActive(employeeDto.getIsActive());
        employee.setCccd(employeeDto.getCccd());

        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateLateLoginEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFound::new);
        if( employee.getLastLogin() != null ) {
            return null;
        }
        employee.setLastLogin(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFound::new);
        return mapper.convertToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeByFilter(FilterUser filterUser) {
        Employee employee = employeeRepository.getEmployeeByFilter(filterUser.getId(), filterUser.getEmail(), filterUser.getPhoneNumber(), filterUser.getCccd(), filterUser.getIsActive()).orElseThrow(EmployeeNotFound::new);
        return mapper.convertToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees(Filter filter) {
        List<Employee> employeeList = employeeRepository.getListByFilter(filter.getSearch(),
                filter.getIsLogin(),
                filter.getIsActive(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay()
        );
        return employeeList.stream().map(mapper::convertToEmployeeDto).toList();
    }

    private void checkInfoEmployee(EmployeeDto employeeDto){
        if( employeeRepository.findByCccd(employeeDto.getCccd()).isPresent() ) {
            throw new CCCDDuplicate();
        }
        if( employeeRepository.findByEmail(employeeDto.getEmail()).isPresent() ) {
            throw new EmailDuplicate();
        }
        if( employeeDto.getRole() == null ) {
            throw new RoleNotFound();
        }
    }
}
