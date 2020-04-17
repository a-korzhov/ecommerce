package andrew.korzhov.ecommerce.security.impl;

import andrew.korzhov.ecommerce.domain.Activation;
import andrew.korzhov.ecommerce.domain.repository.ActivationRepository;
import andrew.korzhov.ecommerce.security.ActivationService;
import andrew.korzhov.ecommerce.security.model.User;
import andrew.korzhov.ecommerce.service.MailService;
import andrew.korzhov.ecommerce.service.errors.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

    private final ActivationRepository activationRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public long updateActivation(String code) {
        Activation fromDB = activationRepository.findByActivationCode(code);
        if (fromDB.getActivationCode().equals("Activated")) {
            throw new AlreadyExistsException("User was activated earlier");
        }
        fromDB.setActivationCode("Activated");
        return fromDB.getUserId();
    }

    /*
        Save UUID activation code to database.
     */
    @Override
    @Transactional
    public Activation saveActivationCode(long id) {
        Activation a = new Activation();
        a.setActivationCode(UUID.randomUUID().toString());
        a.setUserId(id);
        return activationRepository.save(a);
    }

    /*
        Send message to user's email
        and taken prepared code from database.
      */
    @Override
    public void sendActivationLink(User user, String code) {
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
}
