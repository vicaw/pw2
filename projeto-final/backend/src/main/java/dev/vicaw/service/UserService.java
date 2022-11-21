package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.user.User;
import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.output.UserLoginOutput;

public interface UserService {

    public List<User> list();

    public User create(UserCreateInput userInput);

    public UserLoginOutput login(UserLoginInput loginInput);

}
