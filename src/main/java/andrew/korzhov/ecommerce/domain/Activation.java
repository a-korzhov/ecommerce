package andrew.korzhov.ecommerce.domain;

import andrew.korzhov.ecommerce.security.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activations")
@Getter
@Setter
public class Activation extends BaseEntity {

    private String activationCode;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
