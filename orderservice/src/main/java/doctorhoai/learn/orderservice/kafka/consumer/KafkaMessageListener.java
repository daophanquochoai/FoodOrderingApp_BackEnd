package doctorhoai.learn.orderservice.kafka.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.PaymentIntent;
import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.orderservice.dto.OrderDto;
import doctorhoai.learn.orderservice.exception.exception.OrderNotFoundException;
import doctorhoai.learn.orderservice.kafka.sender.KafkaMessageSender;
import doctorhoai.learn.orderservice.model.Order;
import doctorhoai.learn.orderservice.model.enums.EStatusOrder;
import doctorhoai.learn.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "order_rollback", groupId = "order_rollback")
    public void receiveOrder(EventOrder<OrderDto> message){
        try{
            System.out.println("Roll back ...");
            EventOrder<OrderDto> eventOrder = objectMapper.convertValue(
                    message,
                    new TypeReference<EventOrder<OrderDto>>() {}
            );
            Optional<Order> orderOptional = orderRepository.findOrderByIdAndStatus(eventOrder.getOrder().getId(), EStatusOrder.CREATING);
            if( !orderOptional.isPresent() ){
                throw new OrderNotFoundException();
            }
            orderOptional.get().setStatus(EStatusOrder.CANCEL);
            orderOptional.get().setMessage(eventOrder.getMessage());
            if( orderOptional.get().getPaymentId().getCode().equals("CARD")){
                PaymentIntent paymentIntent = PaymentIntent.retrieve(orderOptional.get().getTransactionCode());
                paymentIntent.cancel();
            }
            orderRepository.save(orderOptional.get());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "order", groupId = "order")
    public void acceptORder(EventOrder<OrderDto> message){
        try{
            EventOrder<OrderDto> eventOrder = objectMapper.convertValue(
                    message,
                    new TypeReference<EventOrder<OrderDto>>() {}
            );
            Optional<Order> orderOptional = orderRepository.findOrderByIdAndStatus(eventOrder.getOrder().getId(), EStatusOrder.CREATING);
            if( !orderOptional.isPresent() ){
                throw new OrderNotFoundException();
            }
            orderOptional.get().setStatus(EStatusOrder.PENDING);
            orderRepository.save(orderOptional.get());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
