package andrew.korzhov.ecommerce.web.response;

import lombok.Data;

@Data
public class GenericResponse {

    private String message;

    public GenericResponse(String message, Object... args) {
        this.message = String.format(message, args);
    }

}