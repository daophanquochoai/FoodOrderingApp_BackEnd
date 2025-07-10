package doctorhoai.learn.orderservice.dto.userservice;

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
    @NotNull(message = "Id can't empty")
    @Min(value = 0, message = "Id can't empty")
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
