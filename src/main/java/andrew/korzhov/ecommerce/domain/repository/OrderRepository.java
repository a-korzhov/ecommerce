package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByIdAndUserId(long id, long userId);

}
