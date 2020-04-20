package andrew.korzhov.ecommerce.service.errors.handler;

import andrew.korzhov.ecommerce.service.errors.AlreadyExistsException;
import andrew.korzhov.ecommerce.service.errors.NotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

    // NOT FOUND
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    // FOUND
    @ExceptionHandler(value = AlreadyExistsException.class)
    protected ResponseEntity<Object> handleFound(RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Data
    static class ApiError {
        private HttpStatus status;
        @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING)
        private LocalDateTime timestamp = LocalDateTime.now();
        private String message;

        public ApiError(HttpStatus status) {
            this.status = status;
        }
    }
}
