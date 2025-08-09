package doctorhoai.learn.authservice.business.aiservice.service.chatservice;

import doctorhoai.learn.authservice.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ChatFeignFallback implements FallbackFactory<ChatFeign> {

    private final HandleFallBack fallBack;

    @Override
    public ChatFeign create(Throwable cause) {
        return new ChatFeign() {
            @Override
            public Flux<String> rag(String message) {
                return null;
            }

            @Override
            public Flux<String> load(String message, MultipartFile file) throws IOException {
                return null;
            }
        };
    }
}
