package doctorhoai.learn.orderservice.dto.voucherservice;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "Name can't empty")
    private String name;
    @NotBlank(message = "Image can't empty")
    private String image;
    private String desc;
    private EStatusCategory status;
    private CategoryDto parent;

    private List<FoodDto> foods;
}
