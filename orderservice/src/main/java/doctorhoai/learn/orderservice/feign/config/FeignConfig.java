package doctorhoai.learn.orderservice.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import doctorhoai.learn.basedomain.exception.Duplicate;
import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.basedomain.exception.ResponseException;
import doctorhoai.learn.basedomain.exception.ServerDownException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new DefaultErrorDecoder();
    }

    public static class DefaultErrorDecoder implements ErrorDecoder {
        private final ErrorDecoder defaultDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            String body = "";
            ResponseException responseException = null;
            if (response.body() != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                    responseException = objectMapper.readValue(body, ResponseException.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            String message = responseException == null ? null : responseException.getMessage();
            switch (response.status()){
                case 404 :
                    throw new NotFound(message);
                case 400 :
                    throw new Duplicate(message);
            }
            // TODO : can xu ly
            throw new ServerDownException("** Service downed !");
        }
    }
}
