package br.com.lucas.santos.workshop.infrastructure.adapters.email;

import br.com.lucas.santos.workshop.bunisses.contractors.externalibs.notification.EmailNotification;
import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class GmailAdapter implements EmailNotification {

    private JavaMailSender javaMailSender;

    public GmailAdapter(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendNotification(EmailNotificationRequestDto emailNotificationRequestDto) throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
        mimeMessageHelper.setTo(emailNotificationRequestDto.email());
        mimeMessageHelper.setSubject(emailNotificationRequestDto.suject());
        mimeMessageHelper.setText(emailNotificationRequestDto.text(), true);
        javaMailSender.send(mimeMailMessage);
    }


}
