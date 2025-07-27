package doctorhoai.learn.aiservice.controller;

import doctorhoai.learn.aiservice.model.ChatbotRequest;
import doctorhoai.learn.aiservice.model.ChatbotResponse;
import doctorhoai.learn.aiservice.service.chatbot.AiChatService;
//import doctorhoai.learn.aiservice.service.chatbot.AiChatWithToolService;
import doctorhoai.learn.aiservice.service.chatbot.RagService;
import doctorhoai.learn.aiservice.service.chatbot.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
@Slf4j
public class ChatController {

//    private final AiChatWithToolService chatService;
    private final AiChatService aiChatService;
    private final RagService ragService;
    private final ResponseService responseService;

    public ChatController(RagService ragService, AiChatService aiChatService, ResponseService responseService) {
//        this.chatService = chatService;
        this.aiChatService = aiChatService;
        this.ragService = ragService;
        this.responseService = responseService;
    }

    @GetMapping
    public Flux<String> rag(@RequestParam(defaultValue = "Hello") String message) {
        // Simple chat endpoint:
        // - Handles basic questions without document context
        // - Returns streaming response
        return aiChatService.chat(message);
    }

    @PostMapping
    public Mono<ChatbotResponse> chat(@RequestBody ChatbotRequest request) {
        ChatbotRequest.ChatData data = request.getVariables().getData();

        String userContent = data.getMessages().stream()
                .filter(msg -> "user".equals(msg.getTextMessage().getRole()))
                .reduce((first, second) -> second)
                .map(msg -> msg.getTextMessage().getContent())
                .orElse("");

        String parentMessageId = data.getMessages().stream()
                .filter(msg -> "user".equals(msg.getTextMessage().getRole()))
                .reduce((first, second) -> second)
                .map(ChatbotRequest.Message::getId)
                .orElse("");

        return aiChatService.chat(userContent)
                .collectList()
                .timeout(Duration.ofMinutes(10))
                .doOnError(e -> log.error("Error during chat request: {}", e.getMessage()))
                .onErrorResume(e -> {
                    log.error("Error in chat endpoint: ", e);
                    return Mono.just(List.of("Xin lỗi, đã xảy ra lỗi khi xử lý yêu cầu của bạn."));
                })
                .map(contentList -> responseService.createResponse(data.getThreadId(), parentMessageId, contentList));
    }

    @PostMapping("/load")
    public Flux<String> load(@RequestParam(defaultValue = "What is the content of the document?") String message,
                             @RequestBody MultipartFile file) throws IOException {
        // Document-aware chat endpoint:
        // 1. Check if file is provided
        if(file == null || file.isEmpty()) {
            log.info("File is empty");
            return aiChatService.chat(message);
        }

        // 2. Process uploaded document
        log.info("Saving document ...");
        Resource resource = ragService.saveDocument(file);
        log.info("Document saved");

        // 3. Extract and store document segments
        log.info("Saving segments ...");
        ragService.saveSegments(resource);
        log.info("Segments saved");

        // 4. Generate response using document context
        return aiChatService.chat(message);
    }
}
