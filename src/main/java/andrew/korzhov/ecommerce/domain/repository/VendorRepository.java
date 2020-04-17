package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    boolean existsByName(String name);

    Vendor findByName(String name);

}
