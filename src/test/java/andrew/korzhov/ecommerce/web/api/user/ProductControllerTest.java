package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.EcommerceApplicationTests;
import andrew.korzhov.ecommerce.domain.Category;
import andrew.korzhov.ecommerce.domain.Product;
import andrew.korzhov.ecommerce.domain.Vendor;
import andrew.korzhov.ecommerce.service.ProductService;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.response.ProductsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(EcommerceApplicationTests.ProductControllerTestConfiguration.class)
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest extends EcommerceApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    String jwtTokenWithUserRole;
    private static final String AUTHORIZATION = "Authorization";
    ProductDto productDto;

    List<ProductDto> productsDtoList;

    @BeforeEach
    public void setUp() {
        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setCategoryId(1L);
        productDto.setName("product-name");
        productDto.setPrice(new BigDecimal("0"));
        productDto.setVendorId(2L);

        productsDtoList = new ArrayList<>();
        productsDtoList.add(productDto);

        jwtTokenWithUserRole = "Bearer_" + jwtTokenProvider.createToken(1L, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void getFreeProducts() throws Exception {
        given(productService.getFreeProducts()).willReturn(productsDtoList);
        mockMvc.perform(get("/api/products/free")
                .header(AUTHORIZATION, jwtTokenWithUserRole)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[0].name", is("product-name")))
                .andExpect(jsonPath("$[0].price", is(0)))
                .andExpect(jsonPath("$[0].vendorId", is(2)))
                .andDo(print())

        ;

    }

    ProductsResponse productsResponse;
    List<Product> productList;
    List<ProductDto> content = new ArrayList<>();

    public ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        dto.setVendorId(product.getVendorId());
        dto.setCategoryId(product.getCategoryId());
        return dto;
    }

    @Test
    void getProducts() throws Exception {
        int size = 3, page = 0;

        productList = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            Product product = new Product();
            product.setId(i);
            product.setName("product" + i);
            product.setPrice(new BigDecimal("25"));
            product.setQuantity(2);

            Category category = new Category();
            category.setId(1L);
            product.setCategory(category);

            Vendor vendor = new Vendor();
            vendor.setId(1L);
            product.setVendor(vendor);

            productList.add(product);
        }
        for(Product p : productList){
            content.add(toDto(p));
        }

        productsResponse = new ProductsResponse();
        productsResponse.setPage(page);
        productsResponse.setSize(size);
        productsResponse.setContent(content);
        productsResponse.setTotalPages(1);

        given(productService.getByPage(page, size)).willReturn(productsResponse);

        mockMvc.perform(get("/api/products?page=" + page + "&size=" + size)
                .header(AUTHORIZATION, jwtTokenWithUserRole)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.size", is(3)))
                .andExpect(jsonPath("$[0].content", is(content.get(0))))
                .andExpect(jsonPath("$.totalPages", is(2)))
        ;
    }

    @Test
    void testGetProducts() {
    }
}