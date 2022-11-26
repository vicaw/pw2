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
import dev.vicaw.model.user.output.UserRetrieveOutput;
import dev.vicaw.repository.UserRepository;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Override
    public List<UserRetrieveOutput> list() {
        return userMapper.toModelList(userRepository.listAll());
    }

    @Override
    public UserRetrieveOutput getById(Long id) {
        return userMapper.toModel(userRepository.findById(id));
    }

    @Transactional
    @Override
    public UserLoginOutput create(UserCreateInput userInput) {
        User user = userMapper.toModel(userInput);

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExists("An account with email address " + user.getEmail() + " already exists.");

        user.setRole(Role.USER);
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));

        userRepository.persist(user);

        String token = Jwt
                .issuer("http://localhost:8080")
                .upn(user.getEmail())
                .groups(user.getRole().toString())
                .claim(Claims.full_name, user.getName())
                .claim(Claims.sub, user.getId())
                .sign();

        UserLoginOutput loginOutput = new UserLoginOutput(token, userMapper.toModel(user));

        return loginOutput;

    }

    @Override
    public UserLoginOutput login(UserLoginInput loginInput) {
        Optional<User> entity = userRepository.findByEmail(loginInput.getEmail());

        if (!entity.isPresent())
            throw new EmailAlreadyExists("Não existe nenhuma conta cadastrada com o email informado.");

        if (!BcryptUtil.matches(loginInput.getPassword(), entity.get().getPassword()))
            throw new EmailAlreadyExists("Senha incorreta.");

        UserRetrieveOutput user = userMapper.toModel(entity.get());

        String token = Jwt
                .issuer("http://localhost:8080")
                .upn(user.getEmail())
                .groups(user.getRole().toString())
                .claim(Claims.full_name, user.getName())
                .claim(Claims.sub, user.getId().toString())
                .sign();

        UserLoginOutput userOutput = new UserLoginOutput(token, user);

        return userOutput;

    }

}
