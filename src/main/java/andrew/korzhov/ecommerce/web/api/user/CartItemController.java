package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.CartService;
import andrew.korzhov.ecommerce.web.api.ApiConstants;
import andrew.korzhov.ecommerce.web.dto.CartItemDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.USER_CART_ITEM)
@RequiredArgsConstructor
public class CartItemController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addCartItem(@RequestBody CartItemDto dto, Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        dto.setUserId(Long.parseLong(principal));
        return ResponseEntity.ok(cartService.save(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse> deleteCartItem(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "productId", required = false) Long productId) {

        if (userId != null) {
            cartService.deleteByUserId(userId);
        } else if (productId != null) {
            cartService.deleteByProductId(productId);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("Cart item not found"));

        return ResponseEntity.ok(new GenericResponse("Cart item deleted"));
    }

}

