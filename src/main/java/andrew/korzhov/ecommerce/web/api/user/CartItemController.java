package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.CartService;
import andrew.korzhov.ecommerce.utils.AuthUserUtil;
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
    public ResponseEntity<CartItemDto> addCartItem(@RequestBody CartItemDto dto, Authentication auth) {
        dto.setUserId(AuthUserUtil.getUserId(auth));
        return ResponseEntity.ok(cartService.save(dto));
    }

    /*
        To remove all products from the cart use only userId param.
        To remove only 1 type of products - use both params.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse> deleteCartItem(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "productId", required = false) Long productId) {
        cartService.deleteByUserId(userId);

        if (productId != null) {
            cartService.deleteByProductId(productId);
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse("Cart item not found"));

        return ResponseEntity.ok(new GenericResponse("Cart item deleted"));
    }

}

