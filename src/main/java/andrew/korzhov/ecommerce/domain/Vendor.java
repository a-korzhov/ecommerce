package andrew.korzhov.ecommerce.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
@Getter
@Setter
public class Vendor extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

}
