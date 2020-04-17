package andrew.korzhov.ecommerce.security.impl;

import andrew.korzhov.ecommerce.domain.Activation;
import andrew.korzhov.ecommerce.domain.repository.ActivationRepository;
import andrew.korzhov.ecommerce.domain.repository.UserRepository;
import andrew.korzhov.ecommerce.security.UserService;
import andrew.korzhov.ecommerce.security.model.Role;
import andrew.korzhov.ecommerce.security.model.Status;
import andrew.korzhov.ecommerce.security.model.User;
import andrew.korzhov.ecommerce.service.MailService;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ActivationRepository activationRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    /*
        Create NOT_ACTIVE user with encoded password.
        Automatically send email message with activation link.
        Saving activation codes to separate table in database.
     */
    @Override
    @Transactional
    public boolean createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.NOT_ACTIVE);

        Activation activation = saveActivationCode(user);
        sendActivationLink(user, activation.getActivationCode());

        userRepository.save(user);

        return true;
    }

    /*
        Searching activation code in database and:
         - if activation code in database equals 'Activated',
           then returning from method.
         - otherwise - change code to 'Activated' line and set ACTIVE to user.
     */
    @Override
    @Transactional
    public boolean activateUser(String code) {
        Activation fromDB = activationRepository.findByActivationCode(code);
        if (fromDB.getActivationCode().equals("Activated")) {
            return false;
        }
        fromDB.setActivationCode("Activated");
        User user = userRepository.findById(fromDB.getUser().getId()).orElseThrow();
        user.setStatus(Status.ACTIVE);
        return true;
    }

    @Override
    @Transactional
    public void setAdminCredentials(String username) {
        User fromDB = getByUsername(username);
        fromDB.setNewRole(Role.ROLE_ADMIN);
    }

    /*
        Block user setting DELETED to provided username.
        Allowed for ROLE_ADMIN
     */
    @Override
    @Transactional
    public GenericResponse deactivateUser(String username) {
        User fromDB = getByUsername(username);
        if (fromDB.getRoles().contains(Role.ROLE_ADMIN)) {
            return new GenericResponse("Admin user cannot be blocked");
        } else
            fromDB.setStatus(Status.DELETED);

        return new GenericResponse("User %s is blocked successfully", username);
    }

    /*
        AuthenticationManager authenticates username and password.
        Then find user by username and
        if user is not Status.DELETED - create JWT token.
        Otherwise, return exception - 'User is blocked'.
     */

    @Override
    public User getByUsername(String username) {
        User fromDB = userRepository.findByUsername(username);
        if (fromDB == null) {
            throw new UsernameNotFoundException(
                    String.format("User %s not found", username)
            );
        }
        return fromDB;
    }

    /*
        Send message to user's email
        and taken prepared code from database.
     */
    private void sendActivationLink(User user, String code) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s \n" +
                            "Welcome to our shop. Please, visit this link to activate your account\n" +
                            "http://localhost:8080/api/activate?code=%s",
                    user.getUsername(),
                    code
            );
            mailService.sendMessage(user.getEmail(), "Activation code", message);
        }
    }

    /*
        Save UUID activation code to database.
     */
    private Activation saveActivationCode(User user) {
        Activation a = new Activation();
        a.setActivationCode(UUID.randomUUID().toString());
        a.setUser(user);
        return activationRepository.save(a);
    }
}
