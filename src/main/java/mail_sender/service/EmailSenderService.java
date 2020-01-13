package mail_sender.service;

import mail_sender.model.MyEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements EmailSender{
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String hostEmailValue;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MyEmail sendEmail(MyEmail myEmail) {
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mail, true);
            helper.setTo(myEmail.getAddress());
            helper.setReplyTo(hostEmailValue);
            helper.setFrom(hostEmailValue);
            helper.setSubject(myEmail.getSubject());
            helper.setText(myEmail.getBody(), true);
        } catch (MessagingException e) {
            logger.error("Error while sending email", e.getMessage());
            return null;
        }
        javaMailSender.send(mail);
        return myEmail;
    }

}
