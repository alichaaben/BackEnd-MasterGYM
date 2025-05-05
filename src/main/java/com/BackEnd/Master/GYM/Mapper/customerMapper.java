package com.BackEnd.Master.GYM.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.BackEnd.Master.GYM.dto.customerDto;
import com.BackEnd.Master.GYM.entity.customer;

@Mapper(componentModel = "spring")
public interface customerMapper {


    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "pack", target = "pack")
    @Mapping(source = "dateDebut", target = "dateDebut")
    @Mapping(source = "dateFin", target = "dateFin")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "profileImage", target = "profileImage")
    customerDto map(customer entity);


    List<customerDto> map(List<customer> entities);


    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "pack", target = "pack")
    @Mapping(source = "dateDebut", target = "dateDebut")
    @Mapping(source = "dateFin", target = "dateFin")
    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "profileImage", target = "profileImage", ignore = true)
    customer unMap(customerDto dto);


    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "pack", target = "pack")
    @Mapping(source = "dateDebut", target = "dateDebut")
    @Mapping(source = "dateFin", target = "dateFin")
    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "profileImage", target = "profileImage", ignore = true)
    void updateEntityFromDto(@MappingTarget customer entity, customerDto dto);
}
