package doctorhoai.learn.foodservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExportVoucher {
    private Integer id;
    private Integer[] foodNew;
    private Integer[] foodOld;
    private Integer[] categoryNew;
    private Integer[] categoryOld;
}
