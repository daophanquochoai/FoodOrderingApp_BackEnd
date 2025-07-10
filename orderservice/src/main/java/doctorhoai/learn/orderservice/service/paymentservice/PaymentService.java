package doctorhoai.learn.orderservice.service.paymentservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.PaymentDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;

public interface PaymentService {

    PaymentDto addPayment(PaymentDto paymentDto);
    PaymentDto updatePayment(PaymentDto paymentDto, Integer id);
    PageObject getPaymentsByFilter(Filter filter);
    PaymentDto getPaymentById(Integer id);
}
