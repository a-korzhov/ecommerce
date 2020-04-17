package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.price = 0")
    List<Product> findByPriceIsZero();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.name = :name, p.price = :price WHERE p.id = :id")
    void update(long id, @Param("name") String name, @Param("price") BigDecimal price);

}
