package andrew.korzhov.ecommerce.web.api;

import andrew.korzhov.ecommerce.security.ActivationService;
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
    private final ActivationService activationService;

    /*
        Activate user by email link.
        Searching activation code in database and:
         - if activation code in database equals 'Activated',
           then returning from method.
         - otherwise - change code to 'Activated' and set ACTIVE to user.
     */
    @GetMapping("/activate")
    public ResponseEntity<GenericResponse> activateUser(@RequestParam String code) {
        userService.activateUser(activationService.updateActivation(code));
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
