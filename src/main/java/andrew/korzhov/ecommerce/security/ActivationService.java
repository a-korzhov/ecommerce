package andrew.korzhov.ecommerce.security;

import andrew.korzhov.ecommerce.domain.Activation;
import andrew.korzhov.ecommerce.security.model.User;

public interface ActivationService {

    long updateActivation(String code);

    Activation saveActivationCode(long userId);

    void sendActivationLink(User user, String code);
}
