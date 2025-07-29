package doctorhoai.learn.foodservice.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDto {
    private Integer id;
    private Integer quantity;
    private Float priceAtTime;
    private Boolean isActive;
}
