package doctorhoai.learn.authservice.business.aiservice.controller;

import doctorhoai.learn.authservice.business.aiservice.service.chatservice.ChatFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatFeign chatFeign;

    @PostMapping
    public Flux<String> rag(@RequestParam(defaultValue = "Hello") String message){
        return Flux.defer(() -> chatFeign.rag(message))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/load")
    public Flux<String> load(@RequestParam(defaultValue = "What is the content of the document?") String message,
                      @RequestBody MultipartFile file) throws IOException{
        return Flux.defer(() -> {
                    try {
                        return chatFeign.load(file.getOriginalFilename(), file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }) // defer để tránh gọi sớm
                .subscribeOn(Schedulers.boundedElastic());
    }
}
