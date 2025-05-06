package com.BackEnd.Master.GYM.dto;

import com.BackEnd.Master.GYM.entity.TrainingSession.SessionType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrainingSessionDto {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private SessionType sessionType;
    private Integer maxParticipants;
    private String sportName;
}