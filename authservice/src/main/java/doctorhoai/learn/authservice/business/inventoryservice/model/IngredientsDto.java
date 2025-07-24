package doctorhoai.learn.authservice.business.inventoryservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IngredientsDto {
    private Integer id;
    @NotBlank(message = "Name can't empty")
    private String name;
    @NotNull(message = "Unit can't empty")
    private EUnitType unit;
    @Min(value = 0, message = "Low threshold should high than 0 ")
    private Integer lowThreshold;
    private Boolean isActive;
}
