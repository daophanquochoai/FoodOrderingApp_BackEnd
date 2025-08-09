package doctorhoai.learn.authservice.business.dashboardservice.controller;

import doctorhoai.learn.authservice.business.dashboardservice.service.DashboardFeign;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardFeign dashboardFeign;

    @GetMapping("/get-total")
    public ResponseEntity<ResponseObject> getDashboardTotal() {
        return dashboardFeign.getDashboardTotal();
    }

    @GetMapping("/get-monthly-revenue/{year}")
    public ResponseEntity<ResponseObject> getMonthlyRevenue(@PathVariable int year) {
        return dashboardFeign.getMonthlyRevenue(year);
    }
    @GetMapping("/get-years")
    public ResponseEntity<ResponseObject> getOrderYears() {
        return dashboardFeign.getOrderYears();
    }
}
