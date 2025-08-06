package doctorhoai.learn.userservice.service.employee;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.userservice.dto.ChangePassword;
import doctorhoai.learn.userservice.dto.EmployeeDto;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.exception.exception.*;
import doctorhoai.learn.userservice.model.Employee;
import doctorhoai.learn.userservice.model.Role;
import doctorhoai.learn.userservice.repository.EmployeeRepository;
import doctorhoai.learn.userservice.repository.RoleRepository;
import doctorhoai.learn.userservice.repository.UserRepository;
import doctorhoai.learn.userservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Mapper mapper;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        checkInfoEmployee(employeeDto , null);
        Role role = roleRepository.findByRoleName(employeeDto.getRole().getRoleName()).orElseThrow(RoleNotFound::new);

        Employee employee = mapper.convertToEmployee(employeeDto);
        employee.setRole(role);
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFound::new);
        checkInfoEmployee(employeeDto, employee);
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setIsActive(employeeDto.getIsActive());
        employee.setCccd(employeeDto.getCccd());

        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateLateLoginEmployee(String username) {
        Employee employee = employeeRepository.findByEmailAndIsActive(username, true).orElseThrow(EmployeeNotFound::new);
        employee.setLastLogin(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByEmailAndIsActive(username, true).orElseThrow(EmployeeNotFound::new);
        return mapper.convertToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeByFilter(FilterUser filterUser) {
        Employee employee = employeeRepository.getEmployeeByFilter(filterUser.getId(), filterUser.getEmail(), filterUser.getPhoneNumber(), filterUser.getCccd(), filterUser.getIsActive()).orElseThrow(EmployeeNotFound::new);
        return mapper.convertToEmployeeDto(employee);
    }

    @Override
    public PageObject getAllEmployees(Filter filter) {

        Pageable pageable ;
        if( filter.getSort().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }


        Page<Employee> employeeList = employeeRepository.getEmployeeByFilter(
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                filter.getEmail() == null || filter.getEmail().isEmpty() ? null : filter.getEmail(),
                filter.getSearch(),
                filter.getCccd() == null || filter.getCccd().isEmpty() ? null : filter.getCccd(),
                filter.getIsActiveEmploy() == null || filter.getIsActiveEmploy().isEmpty() ? null : filter.getIsActiveEmploy(),
                pageable
        );
        return PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(employeeList.getNumberOfElements())
                .data(employeeList.getContent().stream().map(mapper::convertToEmployeeDto).toList())
                .build();
    }

    @Override
    public void updatePassword(String username, ChangePassword newPassword) {
        Employee employee = employeeRepository.findByEmailAndIsActive(username, true).orElseThrow(EmployeeNotFound::new);
        boolean match = passwordEncoder.matches(newPassword.getOldPassword(), employee.getPassword());
        if(match){
            employee.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            employeeRepository.save(employee);
        }else{
            throw new PasswordNotCorrect();
        }
    }

    @Override
    public List<EmployeeDto> getMulEmployee(List<Integer> ids) {
        List<Employee> employeeList = employeeRepository.getMulEmployee(ids);
        return employeeList.stream().map(mapper::convertToEmployeeDto).toList();
    }

    private void checkInfoEmployee(EmployeeDto employeeDto, Employee employee){
        if( userRepository.findByEmailAndIsActive(employeeDto.getEmail(), true).isPresent() &&  ( employee == null || !employee.getEmail().equals(employeeDto.getEmail())) ) {
            throw new EmailDuplicate();
        }
        if( employeeRepository.findByCccdAndIsActive(employeeDto.getCccd(), true).isPresent() && (employee == null ||!employee.getCccd().equals(employeeDto.getCccd())) ) {
            throw new CCCDDuplicate();
        }
        if( employeeRepository.findByEmailAndIsActive(employeeDto.getEmail(), true).isPresent() && ( employee == null || !employee.getEmail().equals(employeeDto.getEmail())) ) {
            throw new EmailDuplicate();
        }
        if( employeeDto.getRole() == null ) {
            throw new RoleNotFound();
        }
    }
}
