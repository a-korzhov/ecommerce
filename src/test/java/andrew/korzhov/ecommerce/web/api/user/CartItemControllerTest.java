package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.CartService;
import andrew.korzhov.ecommerce.web.dto.CartItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CartItemControllerTest {

    @Mock
    CartService cartService;

    @Mock
    Map<String, Object> model;

    @InjectMocks
    CartItemController cartItemController;

    List<CartItemDto> cartItems = new ArrayList<>();

    MockMvc mockMvc;

    CartItemDto e;

    @BeforeEach
    void setUp() {
        e = new CartItemDto();
        e.setTotal(new BigDecimal("1000"));
        e.setProductQuantity(5);
        cartItems.add(e);
        given(cartService.save(e)).willReturn(cartItems.get(0));

        mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }

    @Test
    void addCartItem() {
    }

    @Test
    void deleteCartItem() {
    }
}