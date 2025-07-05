package doctorhoai.learn.authservice.exception.handle;

import doctorhoai.learn.authservice.exception.UnAuthorizedException;
import doctorhoai.learn.basedomain.exception.ResponseException;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
@Slf4j
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

//    @ExceptionHandler({
//            FeignException.class,
//            FeignException.FeignServerException.class,
//            FeignException.FeignClientException.class,
//            ExecutionException.class
//    })
//    public <T extends FeignException> ResponseEntity<ResponseException> handleProxyException( final T e){
//        log.info("** Api Exception Handler controller, handle feign proxy exception**");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                ResponseException.builder()
//                        .message("** Service Down!")
//                        .timestamp(LocalDateTime.now())
//                        .build()
//        );
//    }

    @ExceptionHandler(exception = {
            UnAuthorizedException.class,
            BadRequestException.class,
            ExpiredJwtException.class,
            BadCredentialsException.class,
            MalformedJwtException.class,
            DisabledException.class
    })
    public <T extends RuntimeException>ResponseEntity<ResponseException> handleApiRequestException( final T e){
        log.info("**ApiExceptionHandler controller, handle Api request**");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
               ResponseException.builder()
                       .message("Bad Credentials")
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
