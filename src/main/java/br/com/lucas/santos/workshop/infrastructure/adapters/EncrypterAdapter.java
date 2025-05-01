package br.com.lucas.santos.workshop.infrastructure.adapters;

import br.com.lucas.santos.workshop.bunisses.protocols.Encrypter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncrypterAdapter implements Encrypter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncrypterAdapter(BCryptPasswordEncoder passwordEncoder){
        this.bCryptPasswordEncoder = passwordEncoder;
    }


    @Override
    public String encrypt(String value) {
        return this.bCryptPasswordEncoder.encode(value);
    }
}
