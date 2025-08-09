package doctorhoai.learn.authservice.business.aiservice.service.chatservice;


import doctorhoai.learn.authservice.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

@FeignClient(
        name = "aiservice",
        path = "/chat",
        contextId = "chatFeignBusiness",
        fallbackFactory = ChatFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ChatFeign {
    @PostMapping
    Flux<String> rag(@RequestParam(defaultValue = "Hello") String message);

    @PostMapping("/load")
    Flux<String> load(@RequestParam(defaultValue = "What is the content of the document?") String message,
                             @RequestBody MultipartFile file) throws IOException ;
}
