package dev.vicaw.model.noticia;

import dev.vicaw.model.noticia.output.NoticiaOutput;
import dev.vicaw.model.noticia.input.ArticleInput;
import dev.vicaw.model.noticia.output.NoticiaFeedOutput;
import dev.vicaw.model.user.UserMapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface NoticiaMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    default NoticiaOutput toCommentOutput(Noticia noticia) {
        return NoticiaOutput
                .builder()
                .id(noticia.getId())
                .author(userMapper.toUserBasicOutput(noticia.getAuthor()))
                .titulo(noticia.getTitulo())
                .subtitulo(noticia.getSubtitulo())
                .body(noticia.getBody())
                .createdAt(noticia.getCreatedAt())
                .updatedAt(noticia.getUpdatedAt())
                .category(noticia.getCategory())

                .build();
    }

    Noticia toModel(ArticleInput articleInput);

    // List<NoticiaFeed> toFeedOutputList(List<Noticia> entities);

    // Noticia toModel(Noticia entity);

    // @InheritInverseConfiguration(name = "toModel")
    // Noticia toEntity(Noticia domain);

    // void updateEntityFromModel(Noticia model, @MappingTarget Noticia entity);

    // void updateModelFromEntity(Noticia entity, @MappingTarget Noticia model);

}