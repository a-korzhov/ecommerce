package andrew.korzhov.ecommerce.service;

public interface MailService {

    void sendMessage(String emailTo, String subject, String message);

}
