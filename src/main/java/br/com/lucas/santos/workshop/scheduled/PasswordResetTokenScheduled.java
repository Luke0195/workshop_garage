package br.com.lucas.santos.workshop.scheduled;

import br.com.lucas.santos.workshop.infrastructure.repository.PasswordResetTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenScheduled {

    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;



    @Scheduled(cron =  "0 0 * * * * ")
    void removePasswordTokenWithUsedStatus(){
        log.info("Registros removidos");
        passwordResetTokenJpaRepository.deletePasswordResetTokenByUsed(true);
    }

}
