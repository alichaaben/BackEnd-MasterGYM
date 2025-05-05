package com.BackEnd.Master.GYM.Mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.BackEnd.Master.GYM.dto.RolesDto;
import com.BackEnd.Master.GYM.entity.Roles;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RolesMapper {

    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "description", target = "description")
    RolesDto map(Roles entity);


    
    List<RolesDto> map(List<Roles> entities);

    
    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "users", ignore = true)
    Roles unMap(RolesDto dto);

    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "users", ignore = true)
    void updateEntityFromDto(@MappingTarget Roles entity, RolesDto dto);

}
