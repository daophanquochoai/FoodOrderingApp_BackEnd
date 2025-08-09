package doctorhoai.learn.dashboardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DashboardTotalDto {
    private float totalRevenue;
    private Long totalFoods;
    private Long totalOrders;
    private Long totalSupplier;
}
