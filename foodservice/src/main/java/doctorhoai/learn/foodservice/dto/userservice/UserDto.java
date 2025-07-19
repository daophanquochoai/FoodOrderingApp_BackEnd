package doctorhoai.learn.foodservice.dto.userservice;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String image;
    private String password;
    private String email;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private RoleDto role;
}
