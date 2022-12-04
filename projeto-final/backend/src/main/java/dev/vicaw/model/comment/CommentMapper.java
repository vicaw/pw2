package dev.vicaw.model.comment;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.comment.output.CommentOutput;
import dev.vicaw.model.noticia.NoticiaMapper;
import dev.vicaw.model.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "cdi", uses = { UserMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);

        default CommentOutput toCommentOutput(Comment comment) {
                return CommentOutput
                                .builder()
                                .id(comment.getId())
                                .articleId(comment.getArticleId())
                                .parentId(comment.getParentId() != null ? comment.getParentId() : null)
                                .author(userMapper.toUserBasicOutput(comment.getAuthor()))
                                .body(comment.getBody())
                                .createdAt(comment.getCreatedAt())
                                .children(
                                                comment.getChildren() != null
                                                                ? comment.getChildren().stream().map(
                                                                                child -> this.toCommentOutput(child))
                                                                                .collect(Collectors.toList())
                                                                : null)
                                .build();
        }

        List<CommentOutput> toCommentOutputList(List<Comment> comments);

        Comment toModel(PostCommentInput input);
}