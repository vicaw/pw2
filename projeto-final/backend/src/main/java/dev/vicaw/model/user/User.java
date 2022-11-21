package dev.vicaw.model.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class User {

    private long id;

    private String email;

    @JsonIgnore
    private String password;

    private Role role;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}