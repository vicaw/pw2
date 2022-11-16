package dev.vicaw.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import dev.vicaw.repository.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
    public Optional<UserEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

}
