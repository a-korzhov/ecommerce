package andrew.korzhov.ecommerce.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderEntryDto {

    private Long id;

    private Long productId;

    private Long orderId;

    private int productQuantity;

    private BigDecimal price;

    private BigDecimal total;

}
