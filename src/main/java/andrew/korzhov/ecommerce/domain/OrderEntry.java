package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_entries")
@Getter
@Setter
public class OrderEntry extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

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

}
