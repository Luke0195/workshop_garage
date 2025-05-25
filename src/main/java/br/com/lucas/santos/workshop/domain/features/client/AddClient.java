package br.com.lucas.santos.workshop.domain.features.client;

import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;

public interface AddClient {
    ClientResponseDto add(ClientRequestDto clientRequestDto);
}
