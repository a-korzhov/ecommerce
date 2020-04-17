package andrew.korzhov.ecommerce.web.api.admin;

import andrew.korzhov.ecommerce.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class MainAdminController {

    private final UserService userService;

    /*
        Give admin rights for user.
        Admin only function.
     */
    @PostMapping("/access")
    public void setAdminRole(@RequestParam String username) {
        userService.setAdminCredentials(username);
    }

}
