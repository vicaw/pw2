package dev.vicaw.model.user.output;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dev.vicaw.model.article.Article;
import dev.vicaw.model.comment.Comment;
import dev.vicaw.model.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UserRetrieveOutput {

    private Long id;

    private String email;

    private Role role;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}