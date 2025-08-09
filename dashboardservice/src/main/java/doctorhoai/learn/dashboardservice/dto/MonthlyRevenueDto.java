package doctorhoai.learn.dashboardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class MonthlyRevenueDto {
    private int month;
    private int year;
    private double revenue;
}
