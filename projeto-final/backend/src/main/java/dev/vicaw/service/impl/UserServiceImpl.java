package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.Claims;

import dev.vicaw.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import dev.vicaw.exception.EmailAlreadyExists;
import dev.vicaw.model.user.Role;
import dev.vicaw.model.user.User;
import dev.vicaw.model.user.UserMapper;
import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.output.UserLoginOutput;
import dev.vicaw.repository.UserRepository;
import dev.vicaw.repository.entity.UserEntity;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.toModelList(userRepository.listAll());
    }

    @Transactional
    @Override
    public User create(UserCreateInput userInput) {
        User user = userMapper.toModel(userInput);

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExists("An account with email address " + user.getEmail() + " already exists.");

        user.setRole(Role.USER);
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));

        UserEntity entity = userMapper.toEntity(user);
        userRepository.persist(entity);

        user.setId(entity.getId());

        return user;

    }

    @Transactional
    @Override
    public UserLoginOutput login(UserLoginInput loginInput) {
        Optional<UserEntity> entity = userRepository.findByEmail(loginInput.getEmail());

        if (!entity.isPresent())
            throw new EmailAlreadyExists("Não existe nenhuma conta cadastrada com o email informado.");

        if (!BcryptUtil.matches(loginInput.getPassword(), entity.get().getPassword()))
            throw new EmailAlreadyExists("Senha incorreta.");

        User user = userMapper.toModel(entity.get());
        user.setPassword(null);
        user.setCreatedAt(null);
        user.setUpdatedAt(null);

        String token = Jwt
                .issuer("http://localhost:8080")
                .upn(user.getEmail())
                .groups(user.getRole().toString())
                .claim(Claims.full_name, user.getName())
                .claim(Claims.email, user.getEmail())
                .sign();

        UserLoginOutput userOutput = new UserLoginOutput(token, user);

        return userOutput;

    }

}
