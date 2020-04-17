package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
