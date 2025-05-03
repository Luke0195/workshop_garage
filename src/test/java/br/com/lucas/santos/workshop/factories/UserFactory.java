package br.com.lucas.santos.workshop.factories;

import br.com.lucas.santos.workshop.domain.entities.Role;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class UserFactory {

    private UserFactory(){};


    public static UserRequestDto makeUserRequestDto(){
        return new UserRequestDto("any_name", "any_mail@mail.com", "any_password",Set.of("ADMIN", "USER"));
    }

    public static User makeUser(UserRequestDto userRequestDto){
        Set<Role> roles = Set.of(Role.builder().name("ADMIN").id(1L).build(),Role.builder().name("USER").id(1L).build());
        return User.builder()
                .id(UUID.fromString("1cc1d929-1373-4c79-ab13-50d743c25146"))
                .name(userRequestDto.name())
                .email(userRequestDto.email())
                .password(userRequestDto.password())
                .roles(roles)
                .createdAt(LocalDateTime.now())

                .updatedAt(null)
                .build();
    }

    public static UserResponseDto makeUserResponseDto(User user){
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles(),
                user.getCreatedAt(), user.getUpdatedAt());
    }
}
