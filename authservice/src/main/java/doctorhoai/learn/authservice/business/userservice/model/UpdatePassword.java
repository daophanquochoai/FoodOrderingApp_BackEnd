package doctorhoai.learn.authservice.business.userservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassword {
    @NotBlank(message = "Password new is blank")
    private String passwordNew;
    @NotBlank(message = "Password old is blank")
    private String passwordOld;
}
