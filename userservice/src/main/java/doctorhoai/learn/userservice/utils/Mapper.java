package doctorhoai.learn.userservice.utils;

import doctorhoai.learn.userservice.dto.EmployeeDto;
import doctorhoai.learn.userservice.dto.RoleDto;
import doctorhoai.learn.userservice.dto.UserDto;
import doctorhoai.learn.userservice.model.Employee;
import doctorhoai.learn.userservice.model.Role;
import doctorhoai.learn.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserDto convertToUserDto(User user) {
        RoleDto roleDto = new RoleDto();
        if( user.getRole() != null){
            roleDto = convertToRoleDto(user.getRole());
        }
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .image(user.getImage())
                .password(null)
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .lastLogin(user.getLastLogin())
                .role(roleDto)
                .build();
    }

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .image(userDto.getImage())
                .email(userDto.getEmail())
                .isActive(userDto.getIsActive())
                .build();
    }

    public RoleDto convertToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public Role convertToRole(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .build();
    }

    public EmployeeDto convertToEmployeeDto(Employee employee ){
        RoleDto roleDto = new RoleDto();
        if( employee.getRole() != null){
            roleDto = convertToRoleDto(employee.getRole());
        }
        return EmployeeDto.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .isActive(employee.getIsActive())
                .name(employee.getName())
                .cccd(employee.getCccd())
                .password(null)
                .role(roleDto)
                .build();
    }

    public Employee convertToEmployee(EmployeeDto employeeDto ){
        return Employee.builder()
                .email(employeeDto.getEmail())
                .name(employeeDto.getName())
                .cccd(employeeDto.getCccd())
                .isActive(employeeDto.getIsActive())
                .build();
    }
}
