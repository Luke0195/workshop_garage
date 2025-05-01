package br.com.lucas.santos.workshop.bunisses.service;

import br.com.lucas.santos.workshop.domain.usecases.user.AddUser;
import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AddUser {
    @Override
    public UserResponseDto add(UserRequestDto userRequestDto) {
        return null;
    }
}
