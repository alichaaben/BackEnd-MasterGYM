package com.BackEnd.Master.GYM.Mapper;


import com.BackEnd.Master.GYM.dto.TrainingSessionDto;
import com.BackEnd.Master.GYM.entity.TrainingSession;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrainingSessionMapper {
    TrainingSessionDto map(TrainingSession entity);
    List<TrainingSessionDto> map(List<TrainingSession> entities);
    TrainingSession unMap(TrainingSessionDto dto);
    void updateEntityFromDto(@MappingTarget TrainingSession entity, TrainingSessionDto dto);
}