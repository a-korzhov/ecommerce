package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.FullyProductDto;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import andrew.korzhov.ecommerce.web.response.ProductsResponse;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto p);

    ProductDto fullyCreateProduct(FullyProductDto p);

    List<ProductDto> getFreeProducts();

    ProductsResponse getSortedByCategoryIdAndPrice(long cId, int page, int size);

    ProductsResponse getByPage(int page, int size);

    GenericResponse deleteById(long id);

    ProductDto updateProduct(long id, ProductDto p);
}
