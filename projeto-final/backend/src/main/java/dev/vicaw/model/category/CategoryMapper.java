package dev.vicaw.model.category;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import dev.vicaw.model.category.input.CategoryCreateInput;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CategoryMapper {

    // List<Category> toModelList(List<Category> entities);

    // Category toModel(Category entity);

    Category toModel(CategoryCreateInput input);

    // @InheritInverseConfiguration(name = "toModel")
    // Category toEntity(Category domain);

    // void updateEntityFromModel(Category model, @MappingTarget Category entity);

    // void updateModelFromEntity(Category entity, @MappingTarget Category model);

}