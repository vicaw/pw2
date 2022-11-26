package dev.vicaw.model.noticia;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import dev.vicaw.model.user.User;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface NoticiaMapper {

    // List<Noticia> toModelList(List<Noticia> entities);

    // Noticia toModel(Noticia entity);

    // @InheritInverseConfiguration(name = "toModel")
    // Noticia toEntity(Noticia domain);

    // void updateEntityFromModel(Noticia model, @MappingTarget Noticia entity);

    // void updateModelFromEntity(Noticia entity, @MappingTarget Noticia model);

}