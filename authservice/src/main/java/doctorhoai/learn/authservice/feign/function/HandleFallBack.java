package doctorhoai.learn.authservice.feign.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.basedomain.exception.ResponseException;
import doctorhoai.learn.basedomain.exception.ServerDownException;
import doctorhoai.learn.basedomain.response.ResponseObject;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HandleFallBack {
    public ResponseEntity<ResponseObject> processFallback( Throwable cause) {
        try{
            if( cause instanceof FeignException fe){
                int status = fe.status();
                String json =fe.contentUTF8();
                ObjectMapper objectMapper = new ObjectMapper();
                ResponseException responseException = objectMapper.readValue(json, ResponseException.class);
                switch (status){
                    case 404 :
                        throw new NotFound(responseException.getMessage());
                }
                // TODO : chua xu ly
            }
            throw new ServerDownException("** Service downed !");
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(cause.getMessage());
        }
    }
}
