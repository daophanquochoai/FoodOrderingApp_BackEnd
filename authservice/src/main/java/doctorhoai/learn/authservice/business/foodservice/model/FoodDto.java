package doctorhoai.learn.authservice.business.foodservice.model;

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
public class FoodDto {
    private Integer id;
    @NotBlank(message = "Name can't empty")
    private String name;
    private String image;
    private String desc;
    private EStatusFood status;
    private CategoryDto category;

    private List<FoodSizeDto> foodSizes;
}
