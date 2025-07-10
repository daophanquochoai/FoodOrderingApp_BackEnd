package doctorhoai.learn.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShippingFeeConfigDto {
    private Integer id;
    @Min(value = 0, message = "Base fee should high than 0")
    @NotNull(message = "Base Fee can't empty")
    private Float baseFee;
    @Min(value = 0, message = "Fee Per Km should high than 0")
    @NotNull(message = "Fee Per Km can't empty")
    private Float feePerKm;
    @Min(value = 0, message = "Rush Hour Fee should high than 0")
    @NotNull(message = "Rush Hour Fee can't empty")
    private Float rushHourFee;
    @Min(value = 0, message = "Min Order For Fee Shipping should high than 0")
    @NotNull(message = "Min Order For Fee Shipping can't empty")
    private Float minOrderForFeeShipping;
    private Boolean isActive;
}
