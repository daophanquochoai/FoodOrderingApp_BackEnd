package doctorhoai.learn.authservice.business.orderservice.model;

import doctorhoai.learn.authservice.business.foodservice.model.VoucherDto;
import doctorhoai.learn.authservice.feign.userservice.model.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    private Integer id;
    @Valid
    private UserDto userId;
    @Min(value = 0, message = "Total price should high than 0")
    private Float totalPrice;

    private PaymentDto paymentId;
    private String transactionCode;
    private VoucherDto discountApplied;
    private Float shipFee;
    private String address;
    private EStatusOrder status;
    private Integer tableNumber;
    private UserDto shipperId;
    private String name;
    private String phoneNumber;
    private Double cogs;

    private List<OrderItemDto> orderItems;
}
