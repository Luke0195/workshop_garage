package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.bunisses.protocols.db.LoadUserByEmail;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserJpaRepository implements LoadUserByEmail {


    private final UserRepository userRepository;

    public UserJpaRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByEmail(String email) throws ServerError {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("This e-mail has not found"));
    }
}
