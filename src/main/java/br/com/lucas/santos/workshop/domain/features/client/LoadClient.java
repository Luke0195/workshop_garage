package br.com.lucas.santos.workshop.domain.features.client;

import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadClient {
    Page<ClientResponseDto> loadClients(Pageable pageable);
}
