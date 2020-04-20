package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // total amount of products in order
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @PrePersist
    public void createdAt() {
        this.createdAt = OffsetDateTime.now();
    }
}
