package doctorhoai.learn.aiservice.controller;

import doctorhoai.learn.aiservice.service.chatbot.AiChatService;
//import doctorhoai.learn.aiservice.service.chatbot.AiChatWithToolService;
import doctorhoai.learn.aiservice.service.chatbot.RagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
@Slf4j
public class ChatController {

//    private final AiChatWithToolService chatService;
    private final AiChatService aiChatService;
    private final RagService ragService;

    public ChatController(RagService ragService, AiChatService aiChatService) {
//        this.chatService = chatService;
        this.aiChatService = aiChatService;
        this.ragService = ragService;
    }

    @GetMapping
    public Flux<String> rag(@RequestParam(defaultValue = "Hello") String message) {
        // Simple chat endpoint:
        // - Handles basic questions without document context
        // - Returns streaming response
        return aiChatService.chat(message);
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
