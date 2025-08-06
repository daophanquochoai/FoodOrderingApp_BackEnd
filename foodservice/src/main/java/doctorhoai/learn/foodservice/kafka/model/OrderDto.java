package doctorhoai.learn.foodservice.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import doctorhoai.learn.foodservice.dto.VoucherDto;
import doctorhoai.learn.foodservice.dto.userservice.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OrderDto {
    private Integer id;
    private UserDto userId;
    private Float totalPrice;
    private String transactionCode;
    private VoucherDto discountApplied;
    private Float shipFee;
    private String address;
    private EStatusOrder status;
    private Integer tableNumber;
    private UserDto shipperId;
    private String name;
    private String phoneNumber;

    private List<OrderItemDto> orderItems;
}
