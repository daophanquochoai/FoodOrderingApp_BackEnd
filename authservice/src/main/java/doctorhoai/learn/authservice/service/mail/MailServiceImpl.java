package doctorhoai.learn.authservice.service.mail;

import doctorhoai.learn.basedomain.exception.BadException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.email}")
    private String emailSender;

    @Override
    @Async("email")
    public void sendMail(String to, String subject, String content, String domain) {
        try{
            String resetLink = domain + "/account/reset?token=" + content;

            StringBuilder messageData = new StringBuilder();
            messageData.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<head>")
                    .append("<meta charset='UTF-8'>")
                    .append("<style>")
                    .append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }")
                    .append(".container { background-color: white; padding: 20px; border-radius: 8px; max-width: 500px; margin: auto; }")
                    .append("h2 { color: #ff6600; }")
                    .append("a { display: inline-block; margin-top: 20px; background-color: #ff6600; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none; }")
                    .append("a:hover { background-color: #e65c00; }")
                    .append("</style>")
                    .append("</head>")
                    .append("<body>")
                    .append("<div class='container'>")
                    .append("<h2>Yêu cầu khôi phục mật khẩu</h2>")
                    .append("<p>Xin chào,</p>")
                    .append("<p>Bạn đã yêu cầu khôi phục mật khẩu cho tài khoản tại <b>Quán Ăn Ngon</b>.</p>")
                    .append("<p>Vui lòng nhấn vào nút bên dưới để đặt lại mật khẩu:</p>")
                    .append("<a href='").append(resetLink).append("'>Khôi phục mật khẩu</a>")
                    .append("<p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>")
                    .append("<p>Trân trọng,<br/>Quán Ăn Ngon</p>")
                    .append("</div>")
                    .append("</body>")
                    .append("</html>");

            //gui mail
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(emailSender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(String.valueOf(messageData), true);
            log.info("Sending email.....");
            javaMailSender.send(message);
            log.info("Sended email!!");
        } catch (Exception e){
            throw new BadException("Send mail failed");
        }
    }

}
