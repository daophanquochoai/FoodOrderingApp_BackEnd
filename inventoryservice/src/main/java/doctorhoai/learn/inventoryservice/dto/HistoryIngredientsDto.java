package doctorhoai.learn.inventoryservice.dto;

import doctorhoai.learn.inventoryservice.model.enums.EUnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryIngredientsDto {
    private Integer id;

    private Integer quantity;
    private Integer usedUnit;
    private Float pricePerUnit;
    private Float avgPrice;
    private EUnitType unit;
    private LocalDate expiredTime;
    private Boolean isActive;

    private IngredientsDto ingredients;
    private List<IngredientsErrorDto> errors;
}
