package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.protocols.cryptography.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.features.user.AddUser;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.RoleJpaRepository;
import br.com.lucas.santos.workshop.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements AddUser {

    private final Encrypter encrypter;
    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    public UserService(Encrypter encrypter, UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository){
        this.encrypter = encrypter;
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        Optional<User> findUserByEmail = userJpaRepository.findByEmail(userRequestDto.email());
        if(findUserByEmail.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        String hashedPassword = this.encrypter.encrypt(userRequestDto.password());
        if(hashedPassword == null) throw new ServerError();
        Set<Role> rolesEntity = userRequestDto.roles().stream()
                .map(roleName -> roleJpaRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("This role does not exists"))
                ).collect(Collectors.toSet());


        User user = User.builder()
                .name(userRequestDto.name())
                .email(userRequestDto.email())
                .password(hashedPassword)
                .roles(Set.of(Role.builder().name("ADMIN").build()))
                .build();
        user = userJpaRepository.save(user);
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(),
                user.getPassword(), rolesEntity, user.getCreatedAt(), user.getUpdatedAt());
    }
}
