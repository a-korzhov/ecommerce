package andrew.korzhov.ecommerce.security;

import andrew.korzhov.ecommerce.security.model.User;
import andrew.korzhov.ecommerce.web.response.GenericResponse;

public interface UserService {

    boolean createUser(User user);

    void setAdminCredentials(String username);

    User getByUsername(String username);

    GenericResponse deactivateUser(String username);

    boolean activateUser(String code);
}
