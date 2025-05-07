package com.BackEnd.Master.GYM.services;

import com.BackEnd.Master.GYM.entity.TrainingSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface TrainingSessionService {
    TrainingSession findById(Long id);

    List<TrainingSession> findAll();

    List<TrainingSession> findByDateRange(LocalDate startDate, LocalDate endDate);

    TrainingSession create(TrainingSession entity);

    TrainingSession update(TrainingSession entity);

    void delete(Long id);

    // Count all sessions today
    long countByDate(LocalDate date);

    // Count active sessions (currently ongoing)
    @Query("SELECT COUNT(t) FROM TrainingSession t WHERE t.date = :date AND t.startTime <= :currentTime AND t.endTime >= :currentTime")
    long countActiveSessions(LocalDate date, LocalTime currentTime);

}