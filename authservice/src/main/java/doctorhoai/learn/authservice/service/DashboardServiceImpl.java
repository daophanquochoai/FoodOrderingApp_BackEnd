package doctorhoai.learn.authservice.service;

import doctorhoai.learn.authservice.dto.DashboardTotalDto;
import doctorhoai.learn.authservice.dto.MonthlyRevenueDto;
import doctorhoai.learn.authservice.dto.OrderYearDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DashboardTotalDto getDashboardTotal(Integer year) {
        Query query = entityManager.createNativeQuery(
                "CALL ThucTapTotNghiep.getDashboardTotal(?)"
        );
        query.setParameter(1, year);

        Object[] result = (Object[]) query.getSingleResult();

        DashboardTotalDto dto = new DashboardTotalDto();
        dto.setTotalRevenue(result[0] != null ? ((Number) result[0]).floatValue() : 0.0f);
        dto.setTotalFoods(result[1] != null ? ((Number)result[1]).longValue() : 0L);
        dto.setTotalOrders(result[2] != null ? ((Number)result[2]).longValue() : 0L);
        dto.setTotalSupplier(result[3] != null ? ((Number)result[3]).longValue() : 0L);
        return dto;
    }

    @Override
    public List<MonthlyRevenueDto> getMonthlyRevenue(int yearSelect) {
        Query query = entityManager.createNativeQuery("call ThucTapTotNghiep.getMonthlyRevenue(:yearSelect);");
        query.setParameter("yearSelect", yearSelect);
        List<Object[]> results = query.getResultList();

        List<MonthlyRevenueDto> dtos = new ArrayList<>();
        for(Object[] row : results) {
            MonthlyRevenueDto dto = new MonthlyRevenueDto();
            dto.setMonth(row[0] != null ? ((Number)row[0]).intValue() : 0);
            dto.setYear(row[1] != null ? ((Number)row[1]).intValue() : 0);
            dto.setRevenue(row[2] != null ? ((Number)row[2]).doubleValue() : 0.0);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<OrderYearDto> getOrderYears() {
        Query query = entityManager.createNativeQuery("call ThucTapTotNghiep.getOrderYears();");
        List<Object[]> results = query.getResultList();

        List<OrderYearDto> dtos = new ArrayList<>();
        for(Object row : results) {
            OrderYearDto dto = new OrderYearDto();
            dto.setYear(row != null ? ((Number)row).intValue() : 0);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Map<Object, Object> getSellFood(Integer year) {
        Query query = entityManager.createNativeQuery("call ThucTapTotNghiep.getTopBestSellFood(:yearSelect);");
        query.setParameter("yearSelect", year);
        List<Object[]> results = query.getResultList();

        Map<Object, Object> returnValue =  new HashMap<>();
        for(Object[] row : results) {
            returnValue.put(row[0],row[1]);
        }
        return returnValue;
    }
}
