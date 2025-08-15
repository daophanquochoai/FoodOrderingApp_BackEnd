package doctorhoai.learn.authservice.service.mail;

public interface MailService {
    void sendMail(String to, String subject, String content, String domain);
}
