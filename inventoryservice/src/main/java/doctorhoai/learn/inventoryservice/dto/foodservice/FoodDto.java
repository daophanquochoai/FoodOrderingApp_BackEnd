package doctorhoai.learn.inventoryservice.dto.foodservice;

import doctorhoai.learn.inventoryservice.dto.FoodIngredientsDto;
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
    private String name;
    private String image;
    private String desc;
    private EStatusFood status;
    private CategoryDto category;

    private List<FoodIngredientsDto> ingredients;
}
