package doctorhoai.learn.inventoryservice.dto;

import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IngredientsUseDto {
    private Integer id;
    private EUnitType unit;
    private Float quantity;
    private Integer orderId;
    private Boolean isActive;
    private IngredientsDto ingredients;
}
