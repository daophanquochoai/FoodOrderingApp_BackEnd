package doctorhoai.learn.orderservice.dto;

import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PointDto {
    private Integer id;
    @Valid
    private UserDto userId;
    private Boolean isActive;
    @Min(value = 0, message = "Point should high than 0")
    @NotNull(message = "Point can't empty")
    private Integer point;
}
