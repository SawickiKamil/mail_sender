package mail_sender.controller;

import mail_sender.model.MyEmail;
import mail_sender.service.EmailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
public class EmailSenderViewController {

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    public EmailSenderViewController(EmailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @PostMapping("/send")
    private String sendEmail(@ModelAttribute MyEmail myEmail) {
        Context context = new Context();
        context.setVariable("body", myEmail.getBody());
        String templateEmail = templateEngine.process("template-email", context);
        myEmail.setBody(templateEmail);
        emailSender.sendEmail(myEmail);
        return "index";
    }

    @GetMapping("/sender")
    public String senderPage(){
        return "sender";
    }
}
