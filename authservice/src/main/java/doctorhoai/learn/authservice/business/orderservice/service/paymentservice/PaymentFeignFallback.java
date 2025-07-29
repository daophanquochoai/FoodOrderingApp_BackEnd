package doctorhoai.learn.authservice.business.orderservice.service.paymentservice;

import doctorhoai.learn.authservice.business.orderservice.model.Filter;
import doctorhoai.learn.authservice.business.orderservice.model.PaymentDto;
import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import doctorhoai.learn.basedomain.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFeignFallback implements FallbackFactory<PaymentFeign> {

    private final HandleFallBack fallBack;

    @Override
    public PaymentFeign create(Throwable cause) {
        return new PaymentFeign() {
            @Override
            public ResponseEntity<ResponseObject> addPayment(PaymentDto paymentDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updatePayment(Integer id, PaymentDto paymentDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllPayments(Filter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getPaymentById(Integer id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
