package br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography;

import br.com.lucas.santos.workshop.business.contractors.externalibs.cryptography.Encrypter;
import br.com.lucas.santos.workshop.business.contractors.externalibs.cryptography.HashComparer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptAdapter implements Encrypter, HashComparer {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BcryptAdapter(BCryptPasswordEncoder passwordEncoder){
        this.bCryptPasswordEncoder = passwordEncoder;
    }


    @Override
    public String encrypt(String value) {
        return this.bCryptPasswordEncoder.encode(value);
    }

    @Override
    public boolean compare(String value, String hash) {
        return this.bCryptPasswordEncoder.matches(value, hash);
    }
}
