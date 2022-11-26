package dev.vicaw.model.comment.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCommentInput {
    private Long articleId;
    private Long authorId;
    private Long parentId;
    private String body;
}
