package dev.vicaw.model.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import dev.vicaw.model.user.input.UserCreateInput;
import dev.vicaw.model.user.input.UserLoginInput;
import dev.vicaw.model.user.output.UserBasicOutput;
import dev.vicaw.model.user.output.UserRetrieveOutput;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    List<UserRetrieveOutput> toModelList(List<User> entities);

    UserRetrieveOutput toModel(User entity);

    UserBasicOutput toUserBasicOutput(User user);

    User toModel(UserCreateInput input);

    UserLoginInput toLoginInput(User user);

    // @InheritInverseConfiguration(name = "toModel")
    // UserEntity toEntity(User domain);

    // void updateEntityFromModel(User model, @MappingTarget UserEntity entity);

    // void updateModelFromEntity(UserEntity entity, @MappingTarget User model);

}