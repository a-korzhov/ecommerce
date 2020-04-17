package andrew.korzhov.ecommerce.service.errors.handler;

import andrew.korzhov.ecommerce.service.errors.ApiError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> validationErrors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage));

        validationErrors.putAll(result.getGlobalErrors().stream()
                .collect(Collectors.toMap(
                        ObjectError::getObjectName,
                        DefaultMessageSourceResolvable::getDefaultMessage)));

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, result.getObjectName(), validationErrors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

}
