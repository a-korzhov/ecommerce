package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.ProductService;
import andrew.korzhov.ecommerce.web.api.ApiConstants;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.response.ProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.USER_PRODUCTS)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*
        Get method for products with price = 0
     */
    @GetMapping("/free")
    public ResponseEntity<List<ProductDto>> getFreeProducts() {
        return ResponseEntity.ok(productService.getFreeProducts());
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return ResponseEntity
                .ok(productService.getByPage(page, size));
    }

    @GetMapping("/sorted")
    public ResponseEntity<ProductsResponse> getProducts(
            @RequestParam("cId") long categoryId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return ResponseEntity
                .ok(productService.getSortedByCategoryIdAndPrice(categoryId, page, size));
    }
}
