package doctorhoai.learn.authservice.service;


import doctorhoai.learn.authservice.dto.DashboardTotalDto;
import doctorhoai.learn.authservice.dto.MonthlyRevenueDto;
import doctorhoai.learn.authservice.dto.OrderYearDto;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    DashboardTotalDto getDashboardTotal(Integer year);
    List<MonthlyRevenueDto> getMonthlyRevenue(int yearSelect);
    List<OrderYearDto> getOrderYears();
    Map<Object, Object> getSellFood(Integer year);
}
