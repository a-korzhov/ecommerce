package andrew.korzhov.ecommerce.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private BigDecimal total;

    private int productQuantity;

    private Long userId;

    private Long productId;
}
