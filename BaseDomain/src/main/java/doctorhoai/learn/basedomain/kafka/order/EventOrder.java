package doctorhoai.learn.basedomain.kafka.order;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventOrder<T> {
    private T order;
    private List<Integer> cart;
    private Integer voucher;
    private Integer point;
    private String message;
    private EStatusOrder status;
}
