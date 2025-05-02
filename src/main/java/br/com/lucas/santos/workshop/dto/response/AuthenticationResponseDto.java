package br.com.lucas.santos.workshop.dto.response;

public record AuthenticationResponseDto(String token, Long expiredIn) {
}
