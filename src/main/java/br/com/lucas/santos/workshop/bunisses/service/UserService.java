package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.bunisses.protocols.cryptography.Encrypter;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.features.user.AddUser;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.RoleRepository;
import br.com.lucas.santos.workshop.infrastructure.adapters.db.UserRepository;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceAlreadyExistsException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import org.springframework.stereotype.Service;

import java.util.Objects;



@Service
public class UserService implements AddUser {

    private final Encrypter encrypter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserService(Encrypter encrypter, UserRepository userRepository, RoleRepository roleRepository){
        this.encrypter = encrypter;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        User existingUser = userRepository.loadUserByEmail(userRequestDto.email());
        if(existingUser != null) throw new ResourceAlreadyExistsException("This email is already taken!");
        String hashedPassword = this.encrypter.encrypt(userRequestDto.password());
        if(hashedPassword == null) throw new ServerError();
        /*
        Set<Role> rolesEntity = userRequestDto.roles().stream()
                .map(roleName -> f.findByName(roleName).orElseThrow(() -> new RuntimeException("This role does not exists"))
                ).collect(Collectors.toSet());

        User user = User.builder()
                .name(userRequestDto.name())
                .email(userRequestDto.email())
                .password(hashedPassword)
                .roles(rolesEntity)
                .build();
        user = userJpaRepository.save(user);
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(),
                user.getPassword(), rolesEntity, user.getCreatedAt(), user.getUpdatedAt());


         */
        return null;
    }
}
