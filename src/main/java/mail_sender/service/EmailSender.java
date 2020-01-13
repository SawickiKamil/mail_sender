package mail_sender.service;

import mail_sender.model.MyEmail;
import org.springframework.stereotype.Component;

public interface EmailSender {

    MyEmail sendEmail(MyEmail myEmail);

}
