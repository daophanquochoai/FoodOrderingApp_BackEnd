package doctorhoai.learn.authservice.feign.userservice.model;

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
    @NotNull(message = "Name can't empty")
    @NotBlank(message = "Name can't empty")
    private String name;
    @NotNull(message = "Phone Number can't empty")
    @NotBlank(message = "Phone Number can't empty")
    @Size( min = 0, max = 12, message = "Phone Number should have 10-12 digits")
    private String phoneNumber;
    private String image;
    @NotNull(message = "Password can't empty")
    @NotBlank(message = "Password can't empty")
    @Size(min = 8, message = "Password should have more than 10 digits")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
            message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    private String email;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private RoleDto role;
}
