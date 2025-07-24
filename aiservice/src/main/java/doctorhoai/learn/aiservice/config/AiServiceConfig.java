//package doctorhoai.learn.aiservice.config;
//
//
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import dev.langchain4j.model.chat.StreamingChatLanguageModel;
//import dev.langchain4j.service.AiServices;
//import doctorhoai.learn.aiservice.service.chatbot.AiChatWithToolService;
//import doctorhoai.learn.aiservice.service.foodservice.FoodService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class AiServiceConfig {
//
//    private final StreamingChatLanguageModel chatLanguageModel;
//    private final FoodService foodService;
//
//    @Bean
//    public AiChatWithToolService aiChatWithToolService(
//    ) {
//        return AiServices.builder(AiChatWithToolService.class)
//                .streamingChatLanguageModel(chatLanguageModel)
//                .tools(foodService) // Thêm tool service vào đây
//                .build();
//    }
//}