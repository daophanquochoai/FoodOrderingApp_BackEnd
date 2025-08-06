package doctorhoai.learn.orderservice.dto;

import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStatusOrder {
    private String message;
    private EStatusOrder status;
    private Integer shipperId;
}
