package site.ani4h.auth.middleware.logger;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.ani4h.share.common.ApiResponse;
import site.ani4h.share.errors.BaseException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<?> handleCustomException(BaseException e) {
        HttpStatus status = (e.getStatus() != null) ? e.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        if (status.is5xxServerError()) {
            logger.error("Error: {}", e.getMessage(), e);
        } else {
            logger.info("Info: {}", e.getMessage());
        }
        return ResponseEntity.status(status).body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSystemException(Exception e) {
        HttpStatus status = getStatusFromException(e);
        if (status.is5xxServerError()) {
            logger.error("Error: {}", e.getMessage(), e);
        } else {
            logger.warn("Warn: {}", e.getMessage());
        }
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error("Something went wrong in server"));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        logger.error("Validation Error: {}", errors);

        return ResponseEntity
                .badRequest()
                .body("Registration Failed: Please provide valid data.");
    }

    private static HttpStatus getStatusFromException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST; // 400
        } else if (e instanceof ConstraintViolationException) {
            return HttpStatus.BAD_REQUEST; // 400
        } else if (e instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN; // 403
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.METHOD_NOT_ALLOWED; // 405
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            return HttpStatus.UNSUPPORTED_MEDIA_TYPE; // 415
        }

        return HttpStatus.INTERNAL_SERVER_ERROR; // 500
    }
}
