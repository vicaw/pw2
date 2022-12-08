package dev.vicaw.model.category;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import dev.vicaw.model.category.input.CategoryInput;

@Mapper(componentModel = "cdi")
public interface CategoryMapper {

    Category toModel(CategoryInput input);

    void updateEntityFromInput(CategoryInput input, @MappingTarget Category entity);

}