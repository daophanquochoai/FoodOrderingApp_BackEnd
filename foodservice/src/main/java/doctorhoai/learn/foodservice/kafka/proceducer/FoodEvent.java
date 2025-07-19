package doctorhoai.learn.foodservice.kafka.proceducer;

import doctorhoai.learn.basedomain.kafka.MessageTemplate;
import doctorhoai.learn.foodservice.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodEvent {

    private final KafkaTemplate<String, MessageTemplate<FoodDto>> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String foodTopic;

    public void sendEventToTopic( MessageTemplate<FoodDto> data){
        CompletableFuture<SendResult<String,MessageTemplate<FoodDto>>> future = kafkaTemplate.send(foodTopic, data);
        future.whenComplete( (result, throwable) -> {
            if( throwable == null){
                log.info("Send message = [{}] with offset = [{}], partitions = [{}]", data.toString(), result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }else{
                log.error("Unable to send message = [ {} ] due to : {}", data.toString(), throwable.getMessage());
            }
        });
    }

}
