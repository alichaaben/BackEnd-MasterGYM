package com.BackEnd.Master.GYM.repository;

import com.BackEnd.Master.GYM.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainingSessionRepo extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<TrainingSession> findByDate(LocalDate date);

    // Count all sessions today
    long countByDate(LocalDate date);
    
    // Count active sessions (currently ongoing)
    @Query("SELECT COUNT(t) FROM TrainingSession t WHERE t.date = :date AND t.startTime <= :currentTime AND t.endTime >= :currentTime")
    long countActiveSessions(LocalDate date, LocalTime currentTime);
    
    // Count upcoming sessions today
    @Query("SELECT COUNT(t) FROM TrainingSession t WHERE t.date = :date AND t.startTime > :currentTime")
    long countUpcomingSessions(LocalDate date, LocalTime currentTime);
    
    // Count completed sessions today
    @Query("SELECT COUNT(t) FROM TrainingSession t WHERE t.date = :date AND t.endTime < :currentTime")
    long countCompletedSessions(LocalDate date, LocalTime currentTime);

}