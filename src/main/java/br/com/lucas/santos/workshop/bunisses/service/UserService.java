package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.protocols.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.usecases.user.AddUser;
import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserService implements AddUser {

    private final Encrypter encrypter;
    private final UserRepository userRepository;

    public UserService(Encrypter encrypter, UserRepository userRepository){
        this.encrypter = encrypter;
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        Optional<User> findUserByEmail = userRepository.findByEmail(userRequestDto.email());
        if(findUserByEmail.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        String hashedPassword = this.encrypter.encrypt(userRequestDto.password());
        if(Objects.isNull(hashedPassword)) throw new ServerError();
        return null;
    }
}
