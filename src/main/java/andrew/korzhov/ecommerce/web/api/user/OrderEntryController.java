package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.service.OrderEntryService;
import andrew.korzhov.ecommerce.utils.AuthUserUtil;
import andrew.korzhov.ecommerce.web.api.ApiConstants;
import andrew.korzhov.ecommerce.web.dto.OrderEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.USER_ORDER_ENTRY)
@RequiredArgsConstructor
public class OrderEntryController {

    private final OrderEntryService entryService;

    @PostMapping("/{orderId}")
    public ResponseEntity<List<OrderEntryDto>> getResult(
            @PathVariable(name = "orderId") Long id,
            Authentication auth
    ) {
        return ResponseEntity.ok(entryService.getResult(id, AuthUserUtil.getUserId(auth)));
    }

}
