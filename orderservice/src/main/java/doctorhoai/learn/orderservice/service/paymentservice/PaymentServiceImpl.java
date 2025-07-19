package doctorhoai.learn.orderservice.service.paymentservice;

import doctorhoai.learn.basedomain.response.PageObject;
import doctorhoai.learn.orderservice.dto.PaymentDto;
import doctorhoai.learn.orderservice.dto.filter.Filter;
import doctorhoai.learn.orderservice.exception.exception.PaymentNotFoundException;
import doctorhoai.learn.orderservice.model.Payment;
import doctorhoai.learn.orderservice.repository.PaymentRepository;
import doctorhoai.learn.orderservice.utils.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final Mapper mapper;


    @Override
    @Transactional
    public PaymentDto addPayment(PaymentDto paymentDto) {
        Payment payment = mapper.convertToPayment(paymentDto);
        return mapper.convertToPaymentDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto, Integer id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
        payment.setMethodName(paymentDto.getMethodName());
        payment.setIsActive(paymentDto.getIsActive());
        return mapper.convertToPaymentDto(paymentRepository.save(payment));
    }

    @Override
    public PageObject getPaymentsByFilter(Filter filter) {
        Pageable pageable;
        if( filter.getSort().equals("asc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getOrder()));
        }

        Page<Payment> payments = paymentRepository.getPaymentListByFilter(
                filter.getStartDate() == null ? null : filter.getStartDate().atStartOfDay(),
                filter.getEndDate() == null ? null : filter.getEndDate().plusDays(1).atStartOfDay(),
                filter.getIsActive(),
                pageable
        );

        PageObject pageObject = PageObject.builder()
                .page(filter.getPageNo())
                .totalPage(payments.getTotalPages())
                .data(payments.getContent())
                .build();
        return pageObject;
    }

    @Override
    public PaymentDto getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
        return mapper.convertToPaymentDto(payment);
    }
}
