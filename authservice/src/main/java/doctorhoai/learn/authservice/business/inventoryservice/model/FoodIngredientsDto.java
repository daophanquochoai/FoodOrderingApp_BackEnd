package doctorhoai.learn.authservice.business.inventoryservice.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodIngredientsDto {
    private Integer id;
    @Min(value = 0, message = "Quantity should high than 0")
    private Float quantityPerUnit;
    private IngredientsDto ingredients;
    private Boolean isActive;
    private Integer foodId;
}
