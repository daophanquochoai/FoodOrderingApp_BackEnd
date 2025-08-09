package doctorhoai.learn.authservice.business.dashboardservice.service;

import doctorhoai.learn.authservice.business.foodservice.service.categoryservice.CategoryFeignFallback;
import doctorhoai.learn.authservice.config.feign.FeignConfig;
import doctorhoai.learn.basedomain.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "dashboardservice",
        path = "/dashboard",
        contextId = "dashboardFeignBusiness",
        fallbackFactory = DashboardFeignFallback.class,
        configuration = FeignConfig.class
)
public interface DashboardFeign {
    @GetMapping("/get-total")
    ResponseEntity<ResponseObject> getDashboardTotal();

    @GetMapping("/get-monthly-revenue/{year}")
    ResponseEntity<ResponseObject> getMonthlyRevenue(@PathVariable int year);

    @GetMapping("/get-years")
    ResponseEntity<ResponseObject> getOrderYears();
}
