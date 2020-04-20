package andrew.korzhov.ecommerce.web.api.admin;

import andrew.korzhov.ecommerce.security.UserService;
import andrew.korzhov.ecommerce.web.api.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.ADMIN_API_PREFIX)
@RequiredArgsConstructor
public class MainAdminController {

    private final UserService userService;

    /*
        Give admin rights for user.
        Admin only function.
     */
    @PostMapping("/access")
    public ResponseEntity<?> setAdminRole(@RequestParam String username) {
        userService.setAdminCredentials(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

