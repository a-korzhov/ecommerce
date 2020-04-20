package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, Long> {

    List<OrderEntry> findAllByOrderId(long id);

}
