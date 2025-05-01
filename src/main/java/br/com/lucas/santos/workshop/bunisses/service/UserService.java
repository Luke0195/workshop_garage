package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.protocols.Encrypter;
import br.com.lucas.santos.workshop.domain.usecases.user.AddUser;
import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;


// Validar o e-mail do usuário
// Criptografar a senha do usuário
// Salvar o usuário no banco de dados.
@Service
public class UserService implements AddUser {

    private final Encrypter encrypter;

    public UserService(Encrypter encrypter){
        this.encrypter = encrypter;
    }

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        return null;
    }
}
