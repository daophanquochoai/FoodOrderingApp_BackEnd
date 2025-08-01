package doctorhoai.learn.authservice.feign.userservice;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.authservice.feign.userservice.model.EmployeeDto;
import doctorhoai.learn.authservice.feign.userservice.model.Filter.Filter;
import doctorhoai.learn.authservice.feign.userservice.model.Filter.FilterUser;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("authEmployee")
@RequiredArgsConstructor
public class EmployeeFeignFallBack implements FallbackFactory<EmployeeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public EmployeeFeign create(Throwable cause) {
        return new EmployeeFeign() {
            @Override
            public ResponseEntity<ResponseObject> addEmployeeIntoDb(EmployeeDto employeeDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployeeById(Integer id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateEmployee(Integer id, EmployeeDto employeeDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployeeWithFilter(Integer id, String email, String phoneNumber, String cccd, Boolean isActive) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getListEmployeeWithFilter(String search, Boolean isActive, LocalDate startDate, LocalDate endDate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getLateLoginEmployee(Integer id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
