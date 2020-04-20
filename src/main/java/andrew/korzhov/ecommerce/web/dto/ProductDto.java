package andrew.korzhov.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotEmpty(message = "{app.constraint.NotEmpty.name}")
    private String name;

    private BigDecimal price;

    private Long categoryId;

    private Long vendorId;

}
