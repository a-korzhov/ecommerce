package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM cart_entries WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM cart_entries WHERE product_id = :pId", nativeQuery = true)
    void deleteByProductId(@Param("pId") long pId);

    List<CartItem> getAllByUserId(long userId);

}
