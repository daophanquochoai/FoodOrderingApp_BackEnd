package doctorhoai.learn.basedomain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageObject {
    private Integer page;
    private Integer totalPage;
    private Object data;
}
