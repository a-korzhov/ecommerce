package andrew.korzhov.ecommerce.domain;

import andrew.korzhov.ecommerce.security.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at")
    private LocalDate createdAt;

    // total amount of products in order
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

}
