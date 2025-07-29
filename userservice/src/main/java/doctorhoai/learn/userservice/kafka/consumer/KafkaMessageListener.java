package doctorhoai.learn.userservice.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.basedomain.kafka.order.EventOrder;
import doctorhoai.learn.userservice.kafka.model.OrderDto;
import doctorhoai.learn.userservice.kafka.sender.KafkaMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final ObjectMapper objectMapper;
    private final KafkaMessageSender kafkaMessageSender;
    @Value("${spring.kafka.topic.point}")
    private String pointTopic;
    @Value("${spring.kafka.topic.sender_rollback}")
    private String senderRollbackTopic;

    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "point", groupId = "point")
    public void receiveOrder(EventOrder<OrderDto> message){

    }
}
