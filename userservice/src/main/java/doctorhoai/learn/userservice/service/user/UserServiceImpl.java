package doctorhoai.learn.userservice.service.user;

import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.dto.UserDto;
import doctorhoai.learn.userservice.exception.exception.EmailDuplicate;
import doctorhoai.learn.userservice.exception.exception.PhoneNumberDuplicate;
import doctorhoai.learn.userservice.exception.exception.RoleNotFound;
import doctorhoai.learn.userservice.exception.exception.UserNotFound;
import doctorhoai.learn.userservice.model.Role;
import doctorhoai.learn.userservice.model.User;
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


    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        checkInfoUser(userDto);
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
        checkInfoUser(userDto);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id.toString(), "id"));
        user.setName(userDto.getName());
        user.setImage(userDto.getImage());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(String.valueOf(userDto.getPhoneNumber()));
        user.setIsActive(userDto.getIsActive());

        User userSaved = userRepository.save(user);
        return mapper.convertToUserDto(userSaved);
    }

    @Override
    public UserDto updateLateLoginUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id.toString(), "id"));
        if( user.getLastLogin() != null) return null;
        user.setLastLogin(LocalDateTime.now());
        User userSaved = userRepository.save(user);
        return mapper.convertToUserDto(userSaved);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id.toString(), "id"));
        return mapper.convertToUserDto(user);
    }

    @Override
    public UserDto getUserByFilter(FilterUser filter) {
        User user = userRepository.getUserByFilter(filter.getId(), filter.getEmail(), filter.getPhoneNumber(), filter.getIsActive())
                .orElseThrow(() -> new UserNotFound(filter.getId().toString() + ", " + filter.getEmail() + ", " + filter.getPhoneNumber(),"id, email, phoneNumber" ));
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

    public void checkInfoUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new EmailDuplicate();
        }
        if(userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isPresent()){
            throw new PhoneNumberDuplicate();
        };

        if(userDto.getRole() == null ){
            throw new RoleNotFound();
        }
    }
}
