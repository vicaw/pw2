package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.User;

public interface UserService {
    public List<User> list();

    public User save(User user);
}
