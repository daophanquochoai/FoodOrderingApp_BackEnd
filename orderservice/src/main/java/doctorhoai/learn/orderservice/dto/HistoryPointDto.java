package doctorhoai.learn.orderservice.dto;

import doctorhoai.learn.orderservice.dto.userservice.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryPointDto {
    private Integer id;
    private UserDto userId;
    private Boolean isActive;
    private Integer usedPoint;
    private OrderDto orderId;
}
