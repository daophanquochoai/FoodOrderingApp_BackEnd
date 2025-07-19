package doctorhoai.learn.authservice.business.foodservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodSizeDto {
    private Integer id;
    private Float discount;
    private Float readyInMinutes;
    private Float price;
    private FoodDto foodId;
    private SizeDto sizeId;
    private Boolean isActive;
}
