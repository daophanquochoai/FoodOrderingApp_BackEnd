package doctorhoai.learn.authservice.business.userservice.service.address;

import doctorhoai.learn.authservice.business.userservice.model.AddressDto;
import doctorhoai.learn.authservice.business.userservice.model.Filter;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressFeignFallback implements FallbackFactory<AddressFeign> {

    private final HandleFallBack fallBack;

    @Override
    public AddressFeign create(Throwable cause) {
        return new AddressFeign() {
            @Override
            public ResponseEntity<ResponseObject> createAddress(AddressDto addressDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> createAllAddress(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateAddress(Integer id, AddressDto addressDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> setDefault(Integer id, Integer userId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
