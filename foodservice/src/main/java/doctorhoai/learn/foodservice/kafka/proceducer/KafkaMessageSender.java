package doctorhoai.learn.foodservice.kafka.proceducer;

import doctorhoai.learn.basedomain.kafka.MessageTemplate;
import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.foodservice.dto.FoodDto;
import doctorhoai.learn.foodservice.kafka.model.OrderDto;
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
public class KafkaMessageSender {

    private final KafkaTemplate<String, ?> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String apiTopic;

    /**
     * Gửi message Kafka và log kết quả.
     */
    private <T> void sendKafka(String topic, T data) {
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, T> template = (KafkaTemplate<String, T>) kafkaTemplate;

        CompletableFuture<SendResult<String, T>> future = template.send(topic, data);

        String dataStr = (data != null) ? data.toString() : "null";
        logKafka(future ,dataStr);
    }

    public void sendRollBackOrder(EventOrder<OrderDto> event, String topic){
        sendKafka(topic, event);
    }


    public void sendEventToSearchAI( MessageTemplate<FoodDto> data){
        sendKafka(apiTopic, data);
    }

    public <T> void logKafka(CompletableFuture<SendResult<String, T>> future, Object event) {
        String eventStr = (event != null) ? event.toString() : "null";

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("✅ Sent Kafka message [{}] to topic [{}], partition [{}], offset [{}]",
                        eventStr,
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            } else {
                log.error("❌ Failed to send Kafka message [{}]: {}", eventStr, ex.getMessage(), ex);
            }
        });
    }

}
