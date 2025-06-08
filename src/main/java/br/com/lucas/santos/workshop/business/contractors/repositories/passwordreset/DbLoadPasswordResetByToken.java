package br.com.lucas.santos.workshop.business.contractors.repositories.passwordreset;

import br.com.lucas.santos.workshop.domain.entities.PasswordResetToken;

public interface DbLoadPasswordResetByToken {

    PasswordResetToken loadPasswordResetByToken(String token);
}
