package andrew.korzhov.ecommerce.web.response;

import andrew.korzhov.ecommerce.domain.Product;
import andrew.korzhov.ecommerce.service.mapper.ProductMapper;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductsResponse {
    private int page;
    private int size;
    private List<ProductDto> content;
    private int totalPages;

    public ProductsResponse(int size, int page, Page<Product> productsPageable, ProductMapper productMapper) {
        this.page = page;
        this.size = size;
        this.content = productsPageable.getContent().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        this.totalPages = productsPageable.getTotalPages();
    }
}
