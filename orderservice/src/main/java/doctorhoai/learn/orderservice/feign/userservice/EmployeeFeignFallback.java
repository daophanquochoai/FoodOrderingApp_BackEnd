package doctorhoai.learn.orderservice.feign.userservice;

import doctorhoai.learn.basedomain.response.ResponseObject;
import doctorhoai.learn.orderservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeFeignFallback implements FallbackFactory<EmployeeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public EmployeeFeign create(Throwable cause) {
        return new EmployeeFeign() {
            @Override
            public ResponseEntity<ResponseObject> getMulEmployee(List<Integer> ids) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
