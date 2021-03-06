package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.Product;
import andrew.korzhov.ecommerce.domain.repository.ProductRepository;
import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.service.ProductService;
import andrew.korzhov.ecommerce.service.VendorService;
import andrew.korzhov.ecommerce.service.mapper.ProductMapper;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import andrew.korzhov.ecommerce.web.dto.FullyProductDto;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.dto.VendorDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import andrew.korzhov.ecommerce.web.response.ProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final VendorService vendorService;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getFreeProducts() {
        return productRepository.findByPriceIsZero().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductsResponse getSortedByCategoryIdAndPrice(long cId, int page, int size) {
        Page<Product> productPage = productRepository
                .findAllByCategoryId(cId, PageRequest.of(page, size, Sort.by("price").ascending()));
        return new ProductsResponse(size, page, productPage, productMapper);
    }

    @Override
    public ProductsResponse getByPage(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        return new ProductsResponse(size, page, productPage, productMapper);
    }

    /*
        Only name and price are updating.
     */
    @Override
    @Transactional
    public ProductDto updateProduct(final long id, ProductDto p) {
        productRepository.update(id, p.getName(), p.getPrice());
        return p;
    }

    @Override
    @Transactional
    public GenericResponse deleteById(long id) {
        productRepository.deleteById(id);
        return new GenericResponse("Product %s deleted successfully", id);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto p) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(p)));
    }

    @Override
    @Transactional
    public ProductDto fullyCreateProduct(FullyProductDto p) {
        VendorDto v = vendorService.createVendor(p.getVendor());
        CategoryDto c = categoryService.createCategory(p.getCategory());
        return createProduct(
                productMapper.toDtoFromOtherDto(p, v, c));
    }

}
