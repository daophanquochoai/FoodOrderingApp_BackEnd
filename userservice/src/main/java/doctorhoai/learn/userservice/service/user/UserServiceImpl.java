package doctorhoai.learn.userservice.service.user;

import doctorhoai.learn.basedomain.exception.BadException;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.dto.UpdatePassword;
import doctorhoai.learn.userservice.dto.UserDto;
import doctorhoai.learn.userservice.exception.exception.EmailDuplicate;
import doctorhoai.learn.userservice.exception.exception.PhoneNumberDuplicate;
import doctorhoai.learn.userservice.exception.exception.RoleNotFound;
import doctorhoai.learn.userservice.exception.exception.UserNotFound;
import doctorhoai.learn.userservice.feign.orderservice.PointFeign;
import doctorhoai.learn.userservice.model.Role;
import doctorhoai.learn.userservice.model.User;
import doctorhoai.learn.userservice.repository.EmployeeRepository;
import doctorhoai.learn.userservice.repository.RoleRepository;
import doctorhoai.learn.userservice.repository.UserRepository;
import doctorhoai.learn.userservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmployeeRepository employeeRepository;
    private final PointFeign pointFeign;


    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        checkInfoUser(userDto, null);
        Role role = roleRepository.findByRoleName(userDto.getRole().getRoleName()).orElseThrow(RoleNotFound::new);
        User user = mapper.convertToUser(userDto);

        //ma hoa password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);
        //luu
        User userSaved = userRepository.save(user);
        return mapper.convertToUserDto(userSaved);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id.toString(), "id"));
        checkInfoUser(userDto, user);
        user.setName(userDto.getName());
        user.setImage(userDto.getImage());
        user.setPhoneNumber(String.valueOf(userDto.getPhoneNumber()));
        user.setEmail(userDto.getEmail());
        User userSaved = userRepository.save(user);
        return mapper.convertToUserDto(userSaved);
    }

    @Override
    public UserDto updateLateLoginUser(String username) {
        User user = userRepository.getUserByEmail(username).orElseThrow(() -> new UserNotFound(username.toString(), "username"));
        if( user.getLastLogin() != null) return null;
        user.setLastLogin(LocalDateTime.now());
        User userSaved = userRepository.save(user);
        return mapper.convertToUserDto(userSaved);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id == null ? null : id.toString(), "id"));
        if( !user.getIsActive() ){
            throw new UserNotFound(id == null ? null : id.toString(), "id");
        }
        return mapper.convertToUserDto(user);
    }

    @Override
    public UserDto getUserByFilter(FilterUser filter) {
        User user = userRepository.getUserByFilter(filter.getId(), filter.getEmail(), filter.getPhoneNumber(), filter.getIsActive())
                .orElseThrow(() -> new UserNotFound(filter.getId() == null ? null : filter.getId().toString() + ", " + filter.getEmail() + ", " + filter.getPhoneNumber(),"id, email, phoneNumber" ));
        return mapper.convertToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(Filter filter) {
        List<User> userList = userRepository.getListByUser(
                filter.getSearch(),
                filter.getIsLogin(),
                filter.getIsActive(),
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay()
        );
        return userList.stream().map(mapper::convertToUserDto).toList();
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.getUserByEmail(username).orElseThrow(() -> new UserNotFound(username == null ? null : username.toString(), "username"));
        if( !user.getIsActive() ){
            throw new UserNotFound(username == null ? null : username.toString(), "username");
        }
        return mapper.convertToUserDto(user);
    }

    @Override
    public List<UserDto> getMulUser(List<Integer> ids) {
        List<User> userList = userRepository.getMulUser(ids);
        return userList.stream().map(mapper::convertToUserDto).toList();
    }

    @Override
    public void updatePassword(UpdatePassword updatePassword, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id.toString(), "id"));
        if( passwordEncoder.matches(updatePassword.getPasswordOld(), user.getPassword()) ){
            user.setPassword(passwordEncoder.encode(updatePassword.getPasswordNew()));
            userRepository.save(user);
        }else{
            throw new BadException("Password not match");
        }
    }

    @Override
    public void updatePassword(UpdatePassword updatePassword, String email) {
        User user = userRepository.findByEmailAndIsActive(email, true).orElseThrow(() -> new UserNotFound(email, "email"));
        user.setPassword(passwordEncoder.encode(updatePassword.getPasswordNew()));
        userRepository.save(user);
    }


    public void checkInfoUser(UserDto userDto, User user) {
        if(
                employeeRepository.findByEmailAndIsActive(userDto.getEmail(), true).isPresent() &&
                        (user == null || !userDto.getEmail().equals(user.getEmail()) )
        ) {
            throw new EmailDuplicate();
        }
        if(
                userRepository.findByEmailAndIsActive(userDto.getEmail(), true).isPresent() &&
                        ( user == null || !userDto.getEmail().equals(user.getEmail()))
        ){
            throw new EmailDuplicate();
        }
        if(
                userRepository.findByPhoneNumberAndIsActive(userDto.getPhoneNumber(), true).isPresent() &&
                        (user == null || !userDto.getPhoneNumber().equals(user.getPhoneNumber()))
        ){
            throw new PhoneNumberDuplicate();
        };
        if(userDto.getRole() == null ){
            throw new RoleNotFound();
        }
    }
}
