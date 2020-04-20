package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "order_entries")
@Getter
@Setter
public class OrderEntry extends BaseEntity {

    private Long productId;

    private Long orderId;

    @Column(name = "product_quantity")
    private int productQuantity;

    /*
      Price of single requested product
     */
    @Column(name = "price")
    private BigDecimal price;

    /*
      Total amount of same products in order entry
     */
    @Column(name = "total")
    private BigDecimal total;

    @PrePersist
    public void total() {
        this.total = price.multiply(BigDecimal.valueOf(productQuantity));
        if (productQuantity > 3) {
            this.total = this.total.subtract(new BigDecimal("200"));
        }
    }
}
