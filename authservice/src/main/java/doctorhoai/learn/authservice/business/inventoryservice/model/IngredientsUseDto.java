package doctorhoai.learn.authservice.business.inventoryservice.model;

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
    private Integer quantity;
    private Integer orderItemId;
    private Boolean isActive;
}
