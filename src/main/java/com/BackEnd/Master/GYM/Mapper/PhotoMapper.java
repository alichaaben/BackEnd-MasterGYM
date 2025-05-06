package com.BackEnd.Master.GYM.Mapper;

import com.BackEnd.Master.GYM.dto.PhotoDto;
import com.BackEnd.Master.GYM.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "imageName", target = "imageName")
    PhotoDto map(Photo entity);

    List<PhotoDto> map(List<Photo> entities);

    @Mapping(target = "album", ignore = true)
    @Mapping(target = "imageName", ignore = true)
    Photo unMap(PhotoDto dto);

    @Mapping(target = "album", ignore = true)
    @Mapping(target = "imageName", ignore = true)
    void updateEntityFromDto(@MappingTarget Photo entity, PhotoDto dto);
}