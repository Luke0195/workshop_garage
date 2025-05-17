package br.com.lucas.santos.workshop.bunisses.contractors.repositories.passwordreset;

import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;

public interface LoadPasswordResetByToken {

    PasswordResetToken loadPasswordResetByToken(String token);
}
