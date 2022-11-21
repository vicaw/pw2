package dev.vicaw.model.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.repository.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    List<User> toModelList(List<UserEntity> entities);

    User toModel(UserEntity entity);

    User toModel(UserCreateInput input);

    @InheritInverseConfiguration(name = "toModel")
    UserEntity toEntity(User domain);

    void updateEntityFromModel(User model, @MappingTarget UserEntity entity);

    void updateModelFromEntity(UserEntity entity, @MappingTarget User model);

}