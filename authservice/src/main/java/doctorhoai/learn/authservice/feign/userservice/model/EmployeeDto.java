package doctorhoai.learn.authservice.feign.userservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {
    private Integer id;
    @NotNull(message = "Name can't empty")
    @NotBlank(message = "Name can't empty")
    private String name;
    @NotNull(message = "Citizen identification number can't empty")
    @NotBlank(message = "Citizen identification number can't empty")
    private String cccd;
    private Boolean isActive;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
            message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;
    private RoleDto role;
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    private String email;
}
