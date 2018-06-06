package pl.edu.agh.sp.shoppingadvisor.notifications.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@PropertySource("classpath:secrets.properties")
public class EmailService {

  private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

  @Value("${email.username}")
  private String username;

  @Value("${email.password}")
  private String password;


  public void send(EmailMessage emailMessage) {
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(emailMessage.getFrom()));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailMessage.getTo()));
      message.setSubject(emailMessage.getSubject());
      message.setText(emailMessage.getText());

      Transport.send(message);

      logger.info("Email message sent");
    } catch (Exception e) {
      logger.error("Email sending error");
      throw new EmailSendingProblem();
    }
  }
}
