package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.MathContext;

@Entity
@Table(name = "cart_entries")
@Getter
@Setter
public class CartItem extends BaseEntity {

    @Column(name = "product_quantity")
    private int productQuantity;

    private BigDecimal total;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    public BigDecimal getForOne() {
        return this.total.divide(
                BigDecimal.valueOf(this.productQuantity),
                new MathContext(0));
    }
}
