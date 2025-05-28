package br.com.lucas.santos.workshop.business.contractors.externalibs.cryptography;

import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;

public interface TokenGenerator {
    AuthenticationResponseDto generateToken(String userId);
}
