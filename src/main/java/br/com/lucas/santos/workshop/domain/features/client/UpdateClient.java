package br.com.lucas.santos.workshop.domain.features.client;

import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;

public interface UpdateClient {
    ClientResponseDto update(Long id, ClientRequestDto clientRequestDto);
}
