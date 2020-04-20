package andrew.korzhov.ecommerce.domain.repository;

import andrew.korzhov.ecommerce.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.parentId IS NULL")
    List<Category> findAllByWhereIdIsNull();

    List<Category> findAllByParentId(long pId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Category c SET c.name = :name WHERE c.id = :id")
    void updateName(@Param("id") long id, @Param("name") String categoryName);

    boolean existsByName(String name);

    Category findByName(String name);
}
