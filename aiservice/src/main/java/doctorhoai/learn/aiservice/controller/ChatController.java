package doctorhoai.learn.aiservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.aiservice.service.chatbot.AiChatService;
import doctorhoai.learn.aiservice.service.chatbot.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final AiChatService chatService;
    private final RagService ragService;

    @GetMapping()
    public Flux<String> rag(@RequestParam(defaultValue = "Hello") String message) {

        return chatService.chat(message);
    }

    @PostMapping("/load")
    public Flux<String> load(@RequestParam(defaultValue = "What is the content of the document?") String message,
                             @RequestBody MultipartFile file) throws IOException {
        // Document-aware chat endpoint:
        // 1. Check if file is provided
        if(file == null || file.isEmpty()) {
            log.info("File is empty");
            return chatService.chat(message);
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
        return chatService.chat(message);
    }
}
