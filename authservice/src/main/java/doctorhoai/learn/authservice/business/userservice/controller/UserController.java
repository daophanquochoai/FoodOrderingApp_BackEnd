package doctorhoai.learn.authservice.business.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import doctorhoai.learn.authservice.business.userservice.service.user.UserFeign;
import doctorhoai.learn.authservice.feign.userservice.model.UserDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFeign userFeign;

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addUserIntoDb(
            @RequestBody @Valid UserDto userDto
    ) throws JsonProcessingException {
        return userFeign.addUserIntoDb(userDto);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(
            @PathVariable Integer id
    ){
        return userFeign.getUserById(id);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateUser(
            @PathVariable Integer id,
            @RequestBody UserDto userDto
    ){
        return userFeign.updateUser(id, userDto);
    }

    @GetMapping("/filter")
    ResponseEntity<ResponseObject> getUserWithFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) Boolean isActive
    ){
        return userFeign.getUserWithFilter(id, email, phoneNumber, cccd, isActive);
    }

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getListUserWithFilter(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        return userFeign.getListUserWithFilter(search, isActive, startDate, endDate);
    }

    @PutMapping("/late_time/{id}")
    ResponseEntity<ResponseObject> updateLatestUpdateTime(
            @PathVariable Integer id
    ){
        return userFeign.updateLatestUpdateTime(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseObject> getUserByUsername(
            @PathVariable String username
    ){
        return userFeign.getUserByUsername(username);
    }
}
