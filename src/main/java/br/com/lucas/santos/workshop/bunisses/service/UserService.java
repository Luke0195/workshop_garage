package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.protocols.cryptography.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.usecases.user.AddUser;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class UserService implements AddUser {

    private final Encrypter encrypter;
    private final UserJpaRepository userJpaRepository;

    public UserService(Encrypter encrypter, UserJpaRepository userJpaRepository){
        this.encrypter = encrypter;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        Optional<User> findUserByEmail = userJpaRepository.findByEmail(userRequestDto.email());
        if(findUserByEmail.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        String hashedPassword = this.encrypter.encrypt(userRequestDto.password());
        if(hashedPassword == null) throw new ServerError();
        User user = User.builder().name(userRequestDto.name()).email(userRequestDto.email()).password(hashedPassword).build();
        user = userJpaRepository.save(user);
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(),
                user.getPassword(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
