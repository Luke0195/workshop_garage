package br.com.lucas.santos.workshop.domain.features.client;

import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;

public interface LoadClientById {
   ClientResponseDto load(Long id);
}
