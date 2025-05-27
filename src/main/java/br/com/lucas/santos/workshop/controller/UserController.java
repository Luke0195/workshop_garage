package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.bunisses.service.UserService;
import br.com.lucas.santos.workshop.domain.dto.request.UserRequestDto;
import br.com.lucas.santos.workshop.domain.dto.response.UserResponseDto;
import br.com.lucas.santos.workshop.helpers.http.HttpHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserResponseDto> handleAddUser(@Valid @RequestBody UserRequestDto userRequestDto){
       UserResponseDto userResponseDto =  userService.add(userRequestDto);
       return HttpHelper.created(userResponseDto, userResponseDto.id());
    }


}
