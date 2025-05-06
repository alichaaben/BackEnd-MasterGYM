package com.BackEnd.Master.GYM.Mapper;

import com.BackEnd.Master.GYM.dto.AlbumDto;
import com.BackEnd.Master.GYM.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    AlbumDto map(Album entity);

    List<AlbumDto> map(List<Album> entities);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "photos", ignore = true)
    Album unMap(AlbumDto dto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "photos", ignore = true)
    void updateEntityFromDto(@MappingTarget Album entity, AlbumDto dto);
}
