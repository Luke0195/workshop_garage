package br.com.lucas.santos.workshop.business.contractors.externallibs.cryptography;

import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;

public interface TokenGenerator {
    AuthenticationResponseDto generateToken(String userId);
}
