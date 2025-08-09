package doctorhoai.learn.dashboardservice.service;

import doctorhoai.learn.dashboardservice.dto.DashboardTotalDto;
import doctorhoai.learn.dashboardservice.dto.MonthlyRevenueDto;
import doctorhoai.learn.dashboardservice.dto.OrderYearDto;

import java.util.List;

public interface DashboardService {
    DashboardTotalDto getDashboardTotal();
    List<MonthlyRevenueDto> getMonthlyRevenue(int yearSelect);
    List<OrderYearDto> getOrderYears();
}
