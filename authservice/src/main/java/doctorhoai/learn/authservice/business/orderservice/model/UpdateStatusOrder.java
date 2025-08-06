package doctorhoai.learn.authservice.business.orderservice.model;

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
