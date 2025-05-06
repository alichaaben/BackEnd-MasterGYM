package com.BackEnd.Master.GYM.Mapper;

import com.BackEnd.Master.GYM.dto.ContactMessageDto;
import com.BackEnd.Master.GYM.entity.ContactMessage;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {

    @Mapping(source = "status", target = "status")
    ContactMessageDto map(ContactMessage entity);

    List<ContactMessageDto> map(List<ContactMessage> entities);

    @Mapping(source = "status", target = "status")
    ContactMessage unMap(ContactMessageDto dto);

    @Mapping(source = "status", target = "status")
    void updateEntityFromDto(@MappingTarget ContactMessage entity, ContactMessageDto dto);
}