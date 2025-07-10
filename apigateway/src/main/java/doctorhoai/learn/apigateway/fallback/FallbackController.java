package doctorhoai.learn.apigateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class FallbackController {

    private final String message = "Service Not Found";

    @RequestMapping("/proxySupport")
    public ResponseEntity<String> proxySupport() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                      message
                );
    }
}
