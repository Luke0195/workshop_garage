package br.com.lucas.santos.workshop.domain.usecases.user;

import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;

public interface AddUser {

    UserResponseDto add(UserRequestDto userRequestDto);
}
