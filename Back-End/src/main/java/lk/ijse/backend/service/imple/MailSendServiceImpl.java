package lk.ijse.backend.service.imple;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSendServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private String loginAlert(String userName){
        return String.format("""
        <html>
          <body style="font-family: sans-serif;">
            <h2>Login Alert!</h2>
            <p><strong>%s</strong>, you just logged into your account.</p>
            <hr>
            <p style="font-size: 12px; color: gray;">This is an automated email. Please don't reply.</p>
          </body>
        </html>
        """, userName);
    }

    private String registeredAlert(String userName) {
        String dateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format("""
        <html>
          <body style="font-family: sans-serif;">
            <h2>Welcome to  Job - Grids Online Job Portal Website!</h2>
            <p>Hello, <strong>%s</strong>,</p>
            <p>Thank you for registering with us. Your account has been successfully created.</p>
            <p><strong>Registration Date & Time:</strong> %s</p>
            <hr>
            <p style="font-size: 12px; color: gray;">This is an automated message. Please do not reply to this email.</p>
          </body>
        </html>
        """, userName, dateTime);
    }


    public void sendLoggedInEmail(String userName, String toEmail, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(loginAlert(userName), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void sendRegisteredEmail(String name, String email, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(registeredAlert(name), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
