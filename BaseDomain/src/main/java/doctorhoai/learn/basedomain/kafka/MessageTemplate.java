package doctorhoai.learn.basedomain.kafka;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageTemplate<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private EMessageType messageType;
    private T data;
}
