package andrew.korzhov.ecommerce.security.impl;

import andrew.korzhov.ecommerce.domain.repository.UserRepository;
import andrew.korzhov.ecommerce.security.UserService;
import andrew.korzhov.ecommerce.security.model.Role;
import andrew.korzhov.ecommerce.security.model.Status;
import andrew.korzhov.ecommerce.security.model.User;
import andrew.korzhov.ecommerce.service.errors.NotFoundException;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
        Create NOT_ACTIVE user with encoded password.
        Automatically send email message with activation link.
        Saving activation codes to separate table in database.
     */
    @Override
    @Transactional
    public boolean createUser(User user) {
        boolean isPresent = userRepository.findByUsername(user.getUsername()).isPresent();

        if (!isPresent) {
            user.setRoles(Collections.singleton(Role.ROLE_USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(Status.NOT_ACTIVE);

            userRepository.save(user);

            return true;
        }
        return false;
    }

    /*

     */
    @Override
    @Transactional
    public void activateUser(long id) {
        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User %s not found", id)
        ).setStatus(Status.ACTIVE);
    }

    @Override
    @Transactional
    public void setAdminCredentials(String username) {
        getByUsername(username).setNewRole(Role.ROLE_ADMIN);
    }

    /*
        Block user setting DELETED to provided username.
        Allowed for ROLE_ADMIN.
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

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User %s not found", username)
        );
    }

}
