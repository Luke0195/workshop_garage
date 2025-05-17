package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.contractors.repositories.passwordreset.ForgotUserPassword;
import br.com.lucas.santos.workshop.domain.dto.request.ForgotEmailDto;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService{

   private final ForgotUserPassword forgotUserPassword;

   public ForgotPasswordService(UserRepository userRepository, ForgotUserPassword forgotUserPassword){
        this.forgotUserPassword = forgotUserPassword;
   }

   public void forgotUserPassword(ForgotEmailDto forgotEmailDto){
     forgotUserPassword.forgotUserPassword(forgotEmailDto.email());
   }
}
