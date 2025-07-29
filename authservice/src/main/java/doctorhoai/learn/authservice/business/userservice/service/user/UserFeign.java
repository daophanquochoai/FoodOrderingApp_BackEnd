package doctorhoai.learn.authservice.business.userservice.service.user;

import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.authservice.feign.userservice.model.UserDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(
        name = "userservice",
        path = "/user",
        contextId = "userAuthBusiness",
        fallbackFactory = UserFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface UserFeign {

    @PostMapping("/add")
    ResponseEntity<ResponseObject> addUserIntoDb(
            @RequestBody @Valid UserDto userDto
    );

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(
            @PathVariable Integer id
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateUser(
            @PathVariable Integer id,
            @RequestBody @Valid UserDto userDto
    );

    @GetMapping("/filter")
    ResponseEntity<ResponseObject> getUserWithFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) Boolean isActive
    );

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getListUserWithFilter(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    );

    @PutMapping("/late_time/{id}")
    ResponseEntity<ResponseObject> updateLatestUpdateTime(
            @PathVariable Integer id
    );
    @GetMapping("/username/{username}")
    ResponseEntity<ResponseObject> getUserByUsername(
            @PathVariable String username
    );
}
