package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.OrderService;
import andrew.korzhov.ecommerce.utils.AuthUserUtil;
import andrew.korzhov.ecommerce.web.api.ApiConstants;
import andrew.korzhov.ecommerce.web.dto.OrderDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.USER_ORDER)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> save(Authentication auth) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.save(AuthUserUtil.getUserId(auth)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok(new GenericResponse("Order %s canceled successfully", id));
    }
}
