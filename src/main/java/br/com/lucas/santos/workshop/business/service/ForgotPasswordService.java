package br.com.lucas.santos.workshop.business.service;

import br.com.lucas.santos.workshop.business.contractors.repositories.passwordreset.DbForgotUserPassword;
import br.com.lucas.santos.workshop.domain.dto.request.ForgotEmailDto;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService{

   private final DbForgotUserPassword forgotUserPassword;

   public ForgotPasswordService(UserRepository userRepository, DbForgotUserPassword forgotUserPassword){
        this.forgotUserPassword = forgotUserPassword;
   }

   public void forgotUserPassword(ForgotEmailDto forgotEmailDto){
     forgotUserPassword.forgotUserPassword(forgotEmailDto.email());
   }
}
