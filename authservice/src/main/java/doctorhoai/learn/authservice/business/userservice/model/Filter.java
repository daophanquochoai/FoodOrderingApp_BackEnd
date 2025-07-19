package doctorhoai.learn.authservice.business.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {
    private String search;
    private Boolean isActive;
    private Boolean isLogin;
    private LocalDate startDate;
    private LocalDate endDate;

    private String sort;
    private String order;
    private Integer pageNo;
    private Integer pageSize;
    private Integer userId;

}
