package doctorhoai.learn.authservice.controller;

import doctorhoai.learn.authservice.service.DashboardService;
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
    private final DashboardService dashboardService;

    @GetMapping("/get-total/{year}")
    public ResponseEntity<ResponseObject> getDashboardTotal(@PathVariable Integer year) {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get Dashboard Total Successful")
                .data(dashboardService.getDashboardTotal(year))
                .build());
    }

    @GetMapping("/get-monthly-revenue/{year}")
    public ResponseEntity<ResponseObject> getMonthlyRevenue(@PathVariable int year) {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get Monthly Revenue Successful")
                .data(dashboardService.getMonthlyRevenue(year))
                .build());
    }
    @GetMapping("/get-years")
    public ResponseEntity<ResponseObject> getOrderYears() {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get Order Years Successful")
                .data(dashboardService.getOrderYears())
                .build());

    }
    @GetMapping("/get-sell-food/{year}")
    public ResponseEntity<ResponseObject> getSellFood(@PathVariable Integer year) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Get Sell Food Successful")
                        .data(dashboardService.getSellFood(year))
                        .build()
        );
    }
}

