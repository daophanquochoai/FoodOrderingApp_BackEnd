package doctorhoai.learn.inventoryservice.kafka.sender;

import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.inventoryservice.kafka.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaMessageSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTo(EventOrder<OrderDto> event, String topic){
        CompletableFuture<SendResult<String,Object>> future = kafkaTemplate.send(topic, event);
        future.whenComplete( (result, throwable) -> {
            if( throwable == null){
                log.info("Send message = [{}] with offset = [{}], partitions = [{}]", topic.toString(), result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }else{
                log.error("Unable to send message = [ {} ] due to : {}", topic.toString(), throwable.getMessage());
            }
        });
    }
}