package br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography;

import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class BcryptAdapterTest {

    @InjectMocks
    private BcryptAdapter sut;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoderStub;


    @DisplayName("encrypt should returns a hash on success")
    @Test
    void encryptShouldReturnsAHashOnSuccess(){
        Mockito.when(bCryptPasswordEncoderStub.encode(Mockito.anyString())).thenReturn("hashed_value");
        String hashedValue = sut.encrypt("value");
        Assertions.assertEquals("hashed_value", hashedValue);
    }

    @DisplayName("encrypt should be called with correct value")
    @Test
    void encryptShouldBeCalledWithCorrectValue(){
        sut.encrypt("value");
        Mockito.verify(bCryptPasswordEncoderStub).encode("value");
    }

    @DisplayName("encrypt should throws if bcrypt throws")
    @Test
    void encryptShouldThrowsIfBcryptThrows(){
        Mockito.when(bCryptPasswordEncoderStub.encode("any_value")).thenThrow(ServerError.class);
        Assertions.assertThrows(ServerError.class, () -> {
            sut.encrypt("any_value");
        });
    }

    @DisplayName("compare should be called with correct value")
    @Test
    void compareShouldBeCalledWithCorrectValue(){
        Mockito.when(bCryptPasswordEncoderStub.matches("any_value", "hashed_value")).thenReturn(true);
        sut.compare("any_value", "hashed_value");
        Mockito.verify(bCryptPasswordEncoderStub).matches("any_value", "hashed_value");
    }

    @DisplayName("compare should returns true when matches returns true")
    @Test
    void compareShouldReturnsTrueWhenValidBcryptReturnsTrue(){
        Mockito.when(bCryptPasswordEncoderStub.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        boolean result = sut.compare("any_value", "hashed_value");
        Assertions.assertEquals(true, result);
    }

    @DisplayName("compare should returns false when matches returns false")
    @Test
    void compareShouldReturnsTrueWhenValidBcryptReturnsFalse(){
        Mockito.when(bCryptPasswordEncoderStub.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        boolean result = sut.compare("wrong_value", "hashed_value");
        Assertions.assertEquals(false, result);
    }


}