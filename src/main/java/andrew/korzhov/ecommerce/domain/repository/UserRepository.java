package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
