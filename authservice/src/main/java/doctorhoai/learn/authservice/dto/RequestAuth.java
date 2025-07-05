package doctorhoai.learn.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuth {

    @NotBlank(message = "User name can't empty")
    @NotNull(message = "User name can't empty")
    private String username;
    @NotBlank(message = "Password can't empty")
    @NotNull(message = "Password can't empty")
    private String password;
}
