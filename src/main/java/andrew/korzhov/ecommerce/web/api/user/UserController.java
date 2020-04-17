package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.domain.Activation;
import andrew.korzhov.ecommerce.security.ActivationService;
import andrew.korzhov.ecommerce.security.UserService;
import andrew.korzhov.ecommerce.security.model.User;
import andrew.korzhov.ecommerce.service.errors.AlreadyExistsException;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ActivationService activationService;

    /*
        Create not active user.
        To activate user look into andrew.korzhov.ecommerce.web.api.ActivationController
     */
    @PostMapping("/create")
    public ResponseEntity<GenericResponse> createUser(@RequestBody User user) {
        boolean isCreatedBefore = userService.createUser(user);
        if (!isCreatedBefore) {
            throw new AlreadyExistsException("User %s already exists", user.getUsername());
        }
        Activation activation = activationService.saveActivationCode(user.getId());
        activationService.sendActivationLink(user, activation.getActivationCode());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse(
                        "User %s created successfully", user.getUsername())
                );
    }

}
