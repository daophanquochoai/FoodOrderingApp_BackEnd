package doctorhoai.learn.aiservice.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Filter {
    private int pageNo;
    private int pageSize;
    private String keyword;
    private String sortBy;
    private String sortOrder; // "ASC" or "DESC"
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Integer> createBy;
}
