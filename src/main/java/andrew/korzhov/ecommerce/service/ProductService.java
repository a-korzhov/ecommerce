package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.FullyProductDto;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto p);

    ProductDto fullyCreateProduct(FullyProductDto p);

    List<ProductDto> getFreeProducts();

    GenericResponse deleteById(long id);

    ProductDto updateProduct(long id, ProductDto p);
}
