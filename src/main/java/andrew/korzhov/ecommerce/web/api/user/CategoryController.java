package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getOneById(@PathVariable long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    /*
        Get main categories of service.
        I.e categories dont have parentId
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getMainCategories());
    }

    /*
        Get categories by parent category.
        For example: category 'cars' with id=5.
        Then we can get all categories by parentId=5 (mini car, jeep, etc).
     */
    @GetMapping("/child")
    public ResponseEntity<List<CategoryDto>> getCategoriesByParent(@RequestParam(name = "id") long parentId) {
        return ResponseEntity.ok(categoryService.getCategoriesSortedByParentId(parentId));
    }

}
