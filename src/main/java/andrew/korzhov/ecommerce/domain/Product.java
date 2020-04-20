package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;

    public BigDecimal getSum(final int quantity) {
        return price.multiply(
                BigDecimal.valueOf(quantity),
                new MathContext(0)
        );
    }
    public Long getCategoryId(){
        return this.category.getId();
    }

    public Long getVendorId(){
        return this.vendor.getId();
    }

}
