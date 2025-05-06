package com.BackEnd.Master.GYM.services;

import com.BackEnd.Master.GYM.entity.TrainingSession;
import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionService {
    TrainingSession findById(Long id);
    List<TrainingSession> findAll();
    List<TrainingSession> findByDateRange(LocalDate startDate, LocalDate endDate);
    TrainingSession create(TrainingSession entity);
    TrainingSession update(TrainingSession entity);
    void delete(Long id);
}