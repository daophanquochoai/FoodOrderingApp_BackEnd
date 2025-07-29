package doctorhoai.learn.foodservice.kafka.proceducer;

import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.foodservice.kafka.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {

    private final KafkaTemplate<String, EventOrder<OrderDto>> kafkaTemplate;

    public void sendRollBackOrder(EventOrder<OrderDto> event, String topic){
        CompletableFuture<SendResult<String, EventOrder<OrderDto>>> future = kafkaTemplate.send(topic, event);
        future.whenComplete( (result, throwable) -> {
            if( throwable == null){
                log.info("Send message = [{}] with offset = [{}], partitions = [{}]", event.toString(), result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }else{
                log.error("Unable to send message = [ {} ] due to : {}", event.toString(), throwable.getMessage());
            }
        });
    }

}
