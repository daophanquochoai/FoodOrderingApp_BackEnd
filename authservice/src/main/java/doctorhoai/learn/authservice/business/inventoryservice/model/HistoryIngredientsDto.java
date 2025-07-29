package doctorhoai.learn.authservice.business.inventoryservice.model;

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

    private Float quantity;
    private Float usedUnit;
    private Float pricePerUnit;
    private Float avgPrice;
    private EUnitType unit;
    private LocalDate expiredTime;
    private Boolean isActive;

    private IngredientsDto ingredients;
    private List<IngredientsErrorDto> errors;
    private List<IngredientsUseDto> uses;
}
