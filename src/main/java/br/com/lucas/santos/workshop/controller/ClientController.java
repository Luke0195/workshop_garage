package br.com.lucas.santos.workshop.controller;


import br.com.lucas.santos.workshop.business.service.ClientService;
import br.com.lucas.santos.workshop.domain.dto.request.ClientRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.ClientResponseDto;
import br.com.lucas.santos.workshop.helpers.http.HttpHelper;
import jakarta.validation.Valid;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/client")
    public ResponseEntity<ClientResponseDto> handleAddUser(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto clientResponseDto = clientService.add(clientRequestDto);
        return HttpHelper.created(clientResponseDto, clientResponseDto.id());
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<Page<ClientResponseDto>> handleLoadClients(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return HttpHelper.ok(clientService.loadClients(pageRequest));
    }

    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<ClientResponseDto> handleLoadClientById(@PathVariable Long id) {
        ClientResponseDto clientResponseDto = clientService.load(id);
        return HttpHelper.ok(clientResponseDto);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<Void> handleDelete(@PathVariable Long id){
        clientService.remove(id);
        return HttpHelper.noContent();
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<ClientResponseDto> handleUpdate(@PathVariable Long id, @RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDto clientResponseDto = clientService.update(id, clientRequestDto);
        return HttpHelper.ok(clientResponseDto);
    }
}
