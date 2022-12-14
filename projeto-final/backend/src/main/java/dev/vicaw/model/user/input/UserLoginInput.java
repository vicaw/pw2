package dev.vicaw.model.user.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserLoginInput {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 8, max = 128)
    private String password;
}
