package doctorhoai.learn.authservice.business.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Filter {
    private Integer pageNo;
    private Integer pageSize;
    private String search;
    private String sort;
    private String order;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer deep;
    private List<Integer> id;
    private Boolean isActive;
}
