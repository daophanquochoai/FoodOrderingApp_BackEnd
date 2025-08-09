package doctorhoai.learn.dashboardservice.controller;

import lombok.RequiredArgsConstructor;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.dashboardservice.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/get-total")
    public ResponseEntity<ResponseObject> getDashboardTotal() {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Get Dashboard Total Successful")
                .data(dashboardService.getDashboardTotal())
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

    }}
