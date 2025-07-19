package doctorhoai.learn.inventoryservice.dto.orderservice;

import doctorhoai.learn.inventoryservice.dto.foodservice.FoodDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemDto {
    private Integer id;
    @Valid
    private FoodDto foodId;
    @Min(value = 0, message = "Quantity should high than 0")
    private Integer quantity;
    @Min(value = 0, message = "Price at Time should high than 0")
    private Float priceAtTime;
    private Boolean isActive;
}
