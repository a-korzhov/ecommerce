package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {

}
