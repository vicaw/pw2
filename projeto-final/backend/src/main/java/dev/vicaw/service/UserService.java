package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.output.UserRetrieveOutput;
import dev.vicaw.model.user.output.UserLoginOutput;

public interface UserService {

    public List<UserRetrieveOutput> list();

    public UserRetrieveOutput getById(Long id);

    public UserLoginOutput create(UserCreateInput userInput);

    public UserLoginOutput login(UserLoginInput loginInput);

}
