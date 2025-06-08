package br.com.lucas.santos.workshop.domain.dto.response;

public record AuthenticationResponseDto(String token, Long expiredIn) {
}
