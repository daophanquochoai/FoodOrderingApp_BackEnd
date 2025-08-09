package doctorhoai.learn.authservice.business.dashboardservice.service;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardFeignFallback implements FallbackFactory<DashboardFeign> {
    private final HandleFallBack fallBack;

    @Override
    public DashboardFeign create(Throwable cause) {
        return new DashboardFeign() {
            @Override
            public ResponseEntity<ResponseObject> getDashboardTotal() {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getMonthlyRevenue(int year) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getOrderYears() {
                return fallBack.processFallback(cause);
            }
        };
    }
}
