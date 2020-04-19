package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.OrderService;
import andrew.korzhov.ecommerce.web.dto.OrderDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
        public ResponseEntity<OrderDto> save(Authentication auth) {
        String principal = (String) auth.getPrincipal();
        return ResponseEntity.ok(orderService.save(Long.parseLong(principal)));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok(new GenericResponse("Order %s canceled successfully", id));
    }
}
