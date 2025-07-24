package doctorhoai.learn.userservice.controller;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.userservice.controller.contanst.EMessageResponse;
import doctorhoai.learn.userservice.dto.Filter.Filter;
import doctorhoai.learn.userservice.dto.Filter.FilterUser;
import doctorhoai.learn.userservice.dto.UserDto;
import doctorhoai.learn.userservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addUserIntoDb(
            @RequestBody @Valid UserDto userDto
            ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.ADD_USER.getMessage())
                        .data(userService.addUser(userDto))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_USER.getMessage())
                        .data(userService.getUserById(id))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(
            @PathVariable Integer id,
            @RequestBody @Valid UserDto userDto
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_USER.getMessage())
                        .data(userService.updateUser(userDto, id))
                        .build()
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> getUserWithFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) Boolean isActive
            ){
        FilterUser filterUser = new FilterUser(id, email, phoneNumber, cccd, isActive);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_USER.getMessage())
                        .data(userService.getUserByFilter(filterUser))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getListUserWithFilter(
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
                        .data(userService.getAllUsers(filter))
                        .build()
        );
    }

    @PutMapping("/late_time/{id}")
    public ResponseEntity<ResponseObject> updateLatestUpdateTime(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_USER.getMessage())
                        .data(userService.updateLateLoginUser(id))
                        .build()
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseObject> getUserByUsername(
            @PathVariable String username
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_USER.getMessage())
                        .data(userService.getUserByUsername(username))
                        .build()
        );
    }
}
