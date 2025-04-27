package br.com.lucas.santos.workshop.domain.usecases.user;

import br.com.lucas.santos.workshop.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.dto.response.UserResponseDto;

public interface AddUser {

    UserResponseDto add(UserRequestDto userRequestDto);
}
