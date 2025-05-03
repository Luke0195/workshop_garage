package br.com.lucas.santos.workshop.bunisses.protocols.cryptography;

import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;

public interface TokenGenerator {
    AuthenticationResponseDto generateToken(String userId);
}
