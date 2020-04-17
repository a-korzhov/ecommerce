package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Activation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationRepository extends JpaRepository<Activation, Long> {

    Activation findByActivationCode(String code);

}
