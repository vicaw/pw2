package dev.vicaw.model.article;

import dev.vicaw.model.article.input.ArticleInput;
import dev.vicaw.model.article.output.ArticleOutput;
import dev.vicaw.model.user.UserMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ArticleMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    default ArticleOutput toArticleOutput(Article noticia) {
        return ArticleOutput
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

    Article toModel(ArticleInput articleInput);

    // List<NoticiaFeed> toFeedOutputList(List<Noticia> entities);

    // Noticia toModel(Noticia entity);

    // @InheritInverseConfiguration(name = "toModel")
    // Noticia toEntity(Noticia domain);

    // void updateEntityFromModel(Noticia model, @MappingTarget Noticia entity);

    // void updateModelFromEntity(Noticia entity, @MappingTarget Noticia model);

}