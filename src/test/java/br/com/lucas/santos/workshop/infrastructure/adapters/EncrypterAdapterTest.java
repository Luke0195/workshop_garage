package br.com.lucas.santos.workshop.infrastructure.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EncrypterAdapterTest {

    @InjectMocks
    private EncrypterAdapter sut;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @DisplayName("encrypt should returns a hash on success")
    @Test
    void encryptShouldReturnsAHashOnSuccess(){
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("hashed_value");
        EncrypterAdapter sut = makeSut();
        String hashedValue = sut.encrypt("value");
        Assertions.assertEquals("hashed_value", hashedValue);
    }


    public EncrypterAdapter makeSut(){
        return new EncrypterAdapter(bCryptPasswordEncoder);
    }
}