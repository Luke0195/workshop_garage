package br.com.lucas.santos.workshop.controller;

import br.com.lucas.santos.workshop.bunisses.service.AuthenticationService;
import br.com.lucas.santos.workshop.bunisses.service.ForgotPasswordService;
import br.com.lucas.santos.workshop.bunisses.service.ResetPasswordService;
import br.com.lucas.santos.workshop.domain.dto.request.AuthenticationRequestDto;
import br.com.lucas.santos.workshop.domain.dto.request.ForgotEmailDto;
import br.com.lucas.santos.workshop.domain.dto.request.ResetPasswordDto;
import br.com.lucas.santos.workshop.domain.dto.request.ResetPasswordParamsDto;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ForgotPasswordService forgotPasswordService;
    private final ResetPasswordService resetPasswordService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    ForgotPasswordService forgotPasswordService,
                                    ResetPasswordService resetPasswordService){
        this.authenticationService = authenticationService;
        this.forgotPasswordService = forgotPasswordService;
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<AuthenticationResponseDto> handleAuthentication(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto){
      AuthenticationResponseDto authenticationResponseDto = authenticationService.authenticate(authenticationRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(authenticationResponseDto);
    }

    @PostMapping(value = "/forgotpassword")
    public ResponseEntity<Void> handleForgotPassword(@Valid @RequestBody ForgotEmailDto forgotEmailDto){
        forgotPasswordService.forgotUserPassword(forgotEmailDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping(value = "/resetpassword")
    public ResponseEntity<Void> handleResetPassword(
        @RequestParam(name = "token", value = "", required = true) String token,
        @Valid @RequestBody ResetPasswordDto resetPasswordDto
        ){
        ResetPasswordParamsDto resetPasswordParamsDto = new ResetPasswordParamsDto(token, resetPasswordDto.password());
        resetPasswordService.reset(resetPasswordParamsDto);
        return ResponseEntity.ok().build();
    }
}
