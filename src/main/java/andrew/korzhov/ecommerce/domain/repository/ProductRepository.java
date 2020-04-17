package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
