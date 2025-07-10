package doctorhoai.learn.orderservice.dto.voucherservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodDto {
    @NotNull(message = "Id can't empty")
    @Min(value = 0, message = "Id should high than 0")
    private Integer id;
    private String name;
    private String image;
    private String desc;
    private EStatusFood status;
}
