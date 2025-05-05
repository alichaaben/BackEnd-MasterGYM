package com.BackEnd.Master.GYM.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.BackEnd.Master.GYM.dto.AppUserDto;
import com.BackEnd.Master.GYM.entity.AppUsers;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

     // Mapping user -> UserDto
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "motDePasse", target = "motDePasse")
    @Mapping(source = "role.roleName", target = "roleName")
    @Mapping(source = "profileImage", target = "profileImage")
    AppUserDto map(AppUsers entity);


    // Mapping List<User> -> List<AppUserDto>
    List<AppUserDto> map(List<AppUsers> entities);

    // Mapping AppUserDto -> User
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "motDePasse", target = "motDePasse")
    @Mapping(source = "roleName", target = "role", ignore = true)
    @Mapping(source = "profileImage", target = "profileImage", ignore = true)
    AppUsers unMap(AppUserDto dto);

    // Mapping AppUserDto -> User pour mise à jour
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "motDePasse", target = "motDePasse")
    @Mapping(source = "roleName", target = "role", ignore = true)
    @Mapping(source = "profileImage", target = "profileImage", ignore = true)
    void updateEntityFromDto(@MappingTarget AppUsers entity, AppUserDto dto);


}