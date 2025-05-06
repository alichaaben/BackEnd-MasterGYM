package com.BackEnd.Master.GYM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training_sessions")
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private String sportName;
    
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;
    
    private Integer maxParticipants;
    
    public enum SessionType {
        INDIVIDUAL, GROUP, WORKSHOP
    }
}