package dev.vicaw.model.user.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginOutput {
    String token;
    UserRetrieveOutput user;
}
