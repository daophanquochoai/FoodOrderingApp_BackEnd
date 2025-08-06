package doctorhoai.learn.inventoryservice.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Float totalPrice;
    private String transactionCode;
    private Float shipFee;
    private String address;
    private EStatusOrder status;
    private Integer tableNumber;
    private String name;
    private String phoneNumber;

    private List<OrderItemDto> orderItems;
}
