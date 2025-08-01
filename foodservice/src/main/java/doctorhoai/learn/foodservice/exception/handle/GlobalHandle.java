package doctorhoai.learn.foodservice.exception.handle;

import doctorhoai.learn.basedomain.exception.BadException;
import doctorhoai.learn.basedomain.exception.Duplicate;
import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.basedomain.exception.ResponseException;
import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@ControllerAdvice
public class GlobalHandle extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseException.builder()
                        .message(fieldErrors.get(0).getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler({
            FeignException.class,
            FeignException.FeignServerException.class,
            FeignException.FeignClientException.class,
            ExecutionException.class
    })
    public <T extends FeignException> ResponseEntity<ResponseException> handleProxyException( final T e){
        log.info("** Api Exception Handler controller, handle feign proxy exception**");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                ResponseException.builder()
                        .message("** Service Down!")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(value = {Duplicate.class})
    public ResponseEntity<ResponseException> handleDuplicate(Duplicate exception) {
        log.info("**ApiExceptionHandler controller, handle duplicate**");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
    @ExceptionHandler(value = {BadException.class})
    public ResponseEntity<ResponseException> handleBadException(BadException exception) {
        log.info("**ApiExceptionHandler controller, handle BadException**");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
    @ExceptionHandler(value = {NotFound.class})
    public ResponseEntity<ResponseException> handleNotFound(NotFound exception) {
        log.info("**ApiExceptionHandler controller, handle notfound**");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception exception) {
        log.info("**ApiExceptionHandler controller, handle exception**");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseException.builder()
                        .message("Error")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
