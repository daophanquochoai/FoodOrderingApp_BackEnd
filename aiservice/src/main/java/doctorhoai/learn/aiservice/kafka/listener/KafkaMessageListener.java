//package doctorhoai.learn.aiservice.kafka.listener;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import doctorhoai.learn.aiservice.dto.FoodDto;
//import doctorhoai.learn.aiservice.service.semantic.SearchFoodService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.RetryableTopic;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.stereotype.Service;
//import doctorhoai.learn.basedomain.kafka.MessageTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaMessageListener {
//
//    private final SearchFoodService searchFoodService;
//    private final ObjectMapper objectMapper;
//
//    @RetryableTopic(attempts = "4", backoff = @Backoff( delay = 3000, multiplier = 1.5, maxDelay = 15000))
//    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.topic.name}")
//    public void consumerTicket(MessageTemplate<FoodDto> foodDto){
//        FoodDto food = objectMapper.convertValue(foodDto.getData(), FoodDto.class);
//        switch (foodDto.getMessageType()){
//            case CREATE_FOOD -> {
//                searchFoodService.addFood(food);
//            }
//            case UPDATE_FOOD -> {
//                searchFoodService.updateFood(food);
//            }
//            case DELETE_FOOD -> {
//                searchFoodService.removeFood(food);
//            }
//        }
//    }
//}
