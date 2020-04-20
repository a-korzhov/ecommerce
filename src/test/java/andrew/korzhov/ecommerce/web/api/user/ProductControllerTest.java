package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.ProductService;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getFreeProducts() throws Exception {
        ProductDto product = new ProductDto();
        product.setId(1L);
        product.setCategoryId(1L);
        product.setName("product-name");
        product.setPrice(new BigDecimal("0"));
        product.setVendorId(2L);
        List<ProductDto> products = new ArrayList<>();
        products.add(product);

        given(productService.getFreeProducts()).willReturn(products);


        mockMvc.perform(get("/api/products/free")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(product.getName())));

    }

    @Test
    void getProducts() {
    }

    @Test
    void testGetProducts() {
    }
}