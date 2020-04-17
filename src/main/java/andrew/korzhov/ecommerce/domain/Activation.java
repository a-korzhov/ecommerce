package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "activations")
@Getter
@Setter
public class Activation extends BaseEntity {

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "user_id")
    private Long userId;
}
