package br.com.lucas.santos.workshop.infrastructure.adapters.db;

import br.com.lucas.santos.workshop.business.contractors.repositories.user.DbAddUserRepository;
import br.com.lucas.santos.workshop.business.contractors.repositories.user.DbLoadUserByEmailRepository;
import br.com.lucas.santos.workshop.domain.entities.User;
import br.com.lucas.santos.workshop.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UserRepository implements DbLoadUserByEmailRepository, DbAddUserRepository {


    private final UserJpaRepository userJpaRepository;
    public UserRepository(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;

    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public User add(User user) {
        return userJpaRepository.save(user);
    }
}
