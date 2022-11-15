package dev.vicaw.service.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dev.vicaw.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import dev.vicaw.exception.EmailAlreadyExists;
import dev.vicaw.exception.UserNotFoundException;
import dev.vicaw.model.User;
import dev.vicaw.repository.UserRepository;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    @Override
    public List<User> list() {
        return userRepository.listAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExists("An account with email address " + user.getEmail() + " already exists.");

        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        userRepository.persist(user);

        return user;

    }

}
