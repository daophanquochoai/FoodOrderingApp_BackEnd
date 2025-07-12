package doctorhoai.learn.foodservice.dto;

import doctorhoai.learn.foodservice.model.enums.EStatusCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 1L;
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
