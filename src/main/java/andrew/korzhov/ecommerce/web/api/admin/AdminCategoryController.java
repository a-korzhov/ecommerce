package andrew.korzhov.ecommerce.web.api.admin;

import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.web.api.ApiConstants;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstants.ADMIN_CATEGORIES)
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto c) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> renameCategory(
            @PathVariable long id,
            @RequestParam(name = "name") String categoryName) {
        return ResponseEntity.ok(categoryService.renameCategory(id, categoryName));
    }

}
