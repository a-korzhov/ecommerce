package andrew.korzhov.ecommerce.web.api.admin;

import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto c) {
        return ResponseEntity.ok(categoryService.createCategory(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> renameCategory(@PathVariable long id, @RequestParam(name = "name") String categoryName) {
        return ResponseEntity.ok(categoryService.renameCategory(id, categoryName));
    }

}
