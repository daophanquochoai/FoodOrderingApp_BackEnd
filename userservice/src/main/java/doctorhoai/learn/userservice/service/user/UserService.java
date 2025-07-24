package doctorhoai.learn.userservice.service.user;

import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer id);
    UserDto updateLateLoginUser(Integer id);
    UserDto getUserById(Integer id);
    UserDto getUserByFilter(FilterUser filter);
    List<UserDto> getAllUsers(Filter filter);
    UserDto getUserByUsername(String username);
}
