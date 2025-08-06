package doctorhoai.learn.authservice.business.userservice.service.employee;

import doctorhoai.learn.authservice.business.userservice.model.ChangePassword;
import doctorhoai.learn.authservice.business.userservice.model.Filter;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.authservice.feign.userservice.model.EmployeeDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmployeeFeignFallback implements FallbackFactory<EmployeeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public EmployeeFeign create(Throwable cause) {
        return new EmployeeFeign() {
            @Override
            public ResponseEntity<ResponseObject> addEmployeeIntoDb(EmployeeDto employeeDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployeeById(String username) {
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
            public ResponseEntity<ResponseObject> getListEmployeeWithFilter(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getLateLoginEmployee(String username) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updatePassword(String email, ChangePassword newPassword) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
