package dev.vicaw.model.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class User {

    private long id;
    private String email;
    private String password;
    private Role role;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String token;

    public User(UserCreateInput input) {
        this.email = input.getEmail();
        this.password = input.getPassword();
        this.name = input.getName();
    }
}