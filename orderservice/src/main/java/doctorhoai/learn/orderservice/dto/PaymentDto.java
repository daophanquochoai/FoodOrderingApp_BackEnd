package doctorhoai.learn.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDto {
    private Integer id;
    @NotBlank(message = "Method name can't empty")
    private String methodName;
    private String code;
    private Boolean isActive;
}
