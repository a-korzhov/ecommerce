package andrew.korzhov.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private Long id;

    private long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    private BigDecimal totalAmount;

}
