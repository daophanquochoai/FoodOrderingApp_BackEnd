package doctorhoai.learn.orderservice.dto;

import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import doctorhoai.learn.orderservice.dto.voucherservice.VoucherDto;
import doctorhoai.learn.orderservice.model.OrderItem;
import doctorhoai.learn.orderservice.model.Payment;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
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
    private EStatusOrder status;
    private Integer tableNumber;
    private UserDto shipperId;

    private List<OrderItem> orderItems;
}
