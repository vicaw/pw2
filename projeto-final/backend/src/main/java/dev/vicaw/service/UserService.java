package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.user.User;
import dev.vicaw.model.user.UserCreateInput;
import dev.vicaw.model.user.UserLoginInput;

public interface UserService {
    public List<User> list();

    public User create(UserCreateInput userInput);

    public User login(UserLoginInput loginInput);
}
