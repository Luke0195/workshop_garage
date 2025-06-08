package br.com.lucas.santos.workshop.domain.dto.request;

public record ResetPasswordParamsDto(String token, String password) {
}
