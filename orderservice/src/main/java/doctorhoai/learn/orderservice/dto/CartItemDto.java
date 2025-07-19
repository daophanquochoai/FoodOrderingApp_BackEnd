package doctorhoai.learn.orderservice.dto;

import doctorhoai.learn.orderservice.dto.foodservice.FoodSizeDto;
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
public class CartItemDto {

    private Integer id;
    @Valid
    private FoodSizeDto foodId;
    @Min(value = 0, message = "Quantity should high than 0")
    private Integer quantity;
    @Min(value = 0, message = "Price At Time should high than 0")
    private Float priceAtTime;
    private Boolean isActive;
}
