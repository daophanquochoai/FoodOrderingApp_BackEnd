package doctorhoai.learn.userservice.dto.Filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String search;
    private Boolean isActive;
    private Boolean isLogin;
    private LocalDate startDate;
    private LocalDate endDate;
}
