package doctorhoai.learn.userservice.dto.Filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    private List<String> email;
    private List<String> phoneNumber;
    private List<String> cccd;
    private List<Boolean> isActiveEmploy;
    

}
