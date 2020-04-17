package andrew.korzhov.ecommerce.web.api.admin;

import andrew.korzhov.ecommerce.service.ProductService;
import andrew.korzhov.ecommerce.web.dto.FullyProductDto;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;


    /*
        Simple endpoint to add new product
        with already existing category and vendor.
     */
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductDto p) {
        return ResponseEntity.ok(productService.createProduct(p));
    }

    /*
        Method to add new product with new category and vendor.
        If category or vendor already exists in database,
        then it will not be saved again.
     */
    @PostMapping("/add/new")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody @Valid FullyProductDto p) {
        return ResponseEntity.ok(productService.fullyCreateProduct(p));
    }

    /*
        Update method by product id.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> editProduct(
            @PathVariable long id,
            @RequestBody ProductDto p) {
        return ResponseEntity.ok(productService.updateProduct(id, p));
    }

    /*
        Delete method by product id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.deleteById(id));
    }
}
