package dev.vicaw.model.user.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserCreateInput {
    @Email
    @NotNull
    private String email;

    @Size(min = 8, max = 128)
    private String password;

    @NotNull
    @Size(min = 3)
    private String name;
}
