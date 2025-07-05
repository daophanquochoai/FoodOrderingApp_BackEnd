package doctorhoai.learn.userservice.exception.handle;

import doctorhoai.learn.basedomain.exception.Duplicate;
import doctorhoai.learn.basedomain.exception.NotFound;
import doctorhoai.learn.basedomain.exception.ResponseException;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalHandleException extends ResponseEntityExceptionHandler {

    private void logWithSl4j(String message, ETypeLog typeLog, Exception... exception) {
        if (typeLog.equals(ETypeLog.ERROR)) {
            log.error(message, exception);
        } else if (typeLog.equals(ETypeLog.WARNING)) {
            log.warn(message, exception);
        } else if (typeLog.equals(ETypeLog.INFO)) {
            log.info(message, exception);
        }
        {
            log.debug(message, exception);
        }
        logWithSentry(message, exception);
    }

    private void logWithSentry(String message, Exception... exceptions) {
        Sentry.captureMessage(message);
        if (exceptions != null && exceptions.length > 0) {
            Sentry.captureException(exceptions[0]);
        }
    }

    @ExceptionHandler(value = {NotFound.class})
    public ResponseEntity<ResponseException> handleNotFound(NotFound exception) {
        log.info("**ApiExceptionHandler controller, handle notfound**");
        logWithSl4j(exception.getMessage(), ETypeLog.ERROR, exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        logWithSl4j(fieldErrors.get(0).getDefaultMessage(), ETypeLog.ERROR, ex);
        log.info("**ApiExceptionHandler controller, handle validate**");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseException.builder()
                        .message(fieldErrors.get(0).getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(value = {Duplicate.class})
    public ResponseEntity<ResponseException> handleDuplicate(Duplicate exception) {
        log.info("**ApiExceptionHandler controller, handle duplicate**");
        logWithSl4j(exception.getMessage(), ETypeLog.ERROR, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
