package br.com.lucas.santos.workshop.infrastructure.adapters.email;


import br.com.lucas.santos.workshop.domain.dto.request.EmailNotificationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ExtendWith(MockitoExtension.class)
class GmailAdapterTest {

    @MockitoBean
    private JavaMailSender javaMailSender;
    @InjectMocks
    private GmailAdapter sut;
    private EmailNotificationRequestDto emailNotificationRequestDto;
    @Mock
    private SimpleMailMessage simpleMailMessage;


    @BeforeEach
    void setupValue() {
        this.emailNotificationRequestDto = new EmailNotificationRequestDto("any_mail@mail.com", "anySubject", "any_text");
    }
}


