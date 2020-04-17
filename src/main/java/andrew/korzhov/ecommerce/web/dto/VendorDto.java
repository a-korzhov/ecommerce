package andrew.korzhov.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorDto {

    private Long id;

    @NotEmpty(message = "{app.constraint.NotEmpty.name}")
    private String name;

    private String country;

}
