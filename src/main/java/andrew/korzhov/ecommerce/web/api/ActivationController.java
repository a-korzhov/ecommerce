package andrew.korzhov.ecommerce.web.api;

import andrew.korzhov.ecommerce.security.UserService;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActivationController {

    private final UserService userService;

    /*
        Activate user by email link.
     */
    @GetMapping("/activate")
    public ResponseEntity<GenericResponse> activateUser(@RequestParam String code) {
        boolean isActivated = userService.activateUser(code);
        if (!isActivated) {
            throw new RuntimeException("User not activated");
        }
        return ResponseEntity.ok(new GenericResponse("Activation succeed"));
    }

    /*
        Block user.
        Admin only function.
     */
    @PostMapping("/admin/users/block")
    public ResponseEntity<GenericResponse> blockUser(@RequestParam("user") String username) {
        return ResponseEntity.ok(userService.deactivateUser(username));
    }
}
