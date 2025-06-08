package br.com.lucas.santos.workshop.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.username}")
    private String userEmail;
    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender(){
      JavaMailSenderImpl javaMailSender =  new JavaMailSenderImpl();
      javaMailSender.setHost("localhost");
      javaMailSender.setPort(1025);
      javaMailSender.setUsername("");
      javaMailSender.setPassword("");

      Properties props = javaMailSender.getJavaMailProperties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.auth", "false");
      props.put("mail.smtp.starttls.enable", "false");
      props.put("mail.debug", "true");
      return javaMailSender;
    }
}
