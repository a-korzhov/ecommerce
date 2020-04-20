package andrew.korzhov.ecommerce.service.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/*
    Rest API response for errors
 */

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private HttpStatus status;
    private String objectName;
    private Map<String, String> validationErrors;

    public ApiError(HttpStatus status, String objectName, Map<String, String> validationErrors) {
        super();
        this.status = status;
        this.objectName = objectName;
        this.validationErrors = validationErrors;
    }
}
