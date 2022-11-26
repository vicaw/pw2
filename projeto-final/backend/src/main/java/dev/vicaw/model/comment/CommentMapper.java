package dev.vicaw.model.comment;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.vicaw.model.comment.input.PostCommentInput;
import dev.vicaw.model.comment.output.CommentOutput;
import dev.vicaw.model.noticia.NoticiaMapper;
import dev.vicaw.model.user.User;
import dev.vicaw.model.user.UserMapper;
import io.quarkus.vertx.web.Body;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@Mapper(componentModel = "cdi", uses = { UserMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
    // CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    NoticiaMapper noticiaMapper = Mappers.getMapper(NoticiaMapper.class);

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
                                ? comment.getChildren().stream().map(child -> this.toCommentOutput(child))
                                        .collect(Collectors.toList())
                                : null)
                .build();
    }

    List<CommentOutput> toCommentOutputList(List<Comment> comments);

    Comment toModel(PostCommentInput input);
}