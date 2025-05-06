package com.BackEnd.Master.GYM.repository;

import com.BackEnd.Master.GYM.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingSessionRepo extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<TrainingSession> findByDate(LocalDate date);
}