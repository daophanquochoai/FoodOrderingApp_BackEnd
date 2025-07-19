package doctorhoai.learn.foodservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topic.name}")
    private String topic;

    @Bean
    public NewTopic topicFood() {
        return TopicBuilder.name(topic)
                .partitions(3) // Number of partitions
                .replicas(1)   // Number of replicas
                .build();
    }
}
