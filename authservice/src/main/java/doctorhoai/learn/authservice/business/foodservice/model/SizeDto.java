package doctorhoai.learn.authservice.business.foodservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {

    private Integer id;
    @NotBlank(message = "Name can't empty")
    private String name;
    private Boolean isActive;
}
