package doctorhoai.learn.authservice.business.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IngredientsErrorDto {
    private Integer id;
    private EUnitType unit;
    private Integer quantity;
    private String reason;
    private Boolean isActive;

    private HistoryIngredientsDto historyIngredients;
}
