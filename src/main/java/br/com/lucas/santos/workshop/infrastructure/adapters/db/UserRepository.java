package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.bunisses.protocols.db.user.LoadUserByEmailRepository;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ResourceNotFoundException;
import br.com.lucas.santos.workshop.infrastructure.exceptions.ServerError;
import br.com.lucas.santos.workshop.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements LoadUserByEmailRepository {


    private final UserJpaRepository userJpaRepository;

    public UserRepository(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User loadUserByEmail(String email) throws ServerError {
        return userJpaRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("This e-mail has not found"));
    }
}
