package br.com.lucas.santos.workshop.bunisses.mapper;

import br.com.lucas.santos.workshop.bunisses.protocols.DataMapper;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;


@Component
public class UserMapper implements DataMapper<User, UserResponseDto> {

    @Override
    public  UserResponseDto mapToDto(User data) {
        return new UserResponseDto(data.getId(), data.getName(), data.getEmail(), data.getPassword(), data.getCreatedAt(), data.getUpdatedAt());
    }

    @Override
    public  User mapToDomain(UserResponseDto entity) {
        return User.builder()
                .name(entity.name())
                .email(entity.email())
                .password(entity.password())
                .createdAt(entity.createdAt())
                .updatedAt(entity.updatedAt())
                .build();
    }
}
