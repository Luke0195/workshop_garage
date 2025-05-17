package br.com.lucas.santos.workshop.bunisses.service;


import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.features.user.AddUser;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography.BcryptAdapter;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.RoleRepository;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;

import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements AddUser {

    private final BcryptAdapter encrypter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserService(BcryptAdapter encrypter, UserRepository userRepository, RoleRepository roleRepository){
        this.encrypter = encrypter;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        Optional<User> existingUser = userRepository.loadUserByEmail(userRequestDto.email());
        if(existingUser.isPresent()) throw new ResourceAlreadyExistsException("This email is already taken!");
        String hashedPassword = this.encrypter.encrypt(userRequestDto.password());
        if(hashedPassword == null) throw new ServerError();
        Set<Role> rolesEntity = userRequestDto.roles().stream().map(roleRepository::loadUserByRole).collect(Collectors
            .toSet());
        User user = createUserWithParsedValues(userRequestDto, hashedPassword, rolesEntity);
        user = userRepository.add(user);
        return UserResponseDto.mapEntityToDto(user);

    }

    private User createUserWithParsedValues(UserRequestDto userRequestDto, String hashedPassword, Set<Role> roles){
       return User.builder()
            .name(userRequestDto.name())
            .email(userRequestDto.email())
            .password(hashedPassword)
            .roles(roles)
            .build();
    }
}
