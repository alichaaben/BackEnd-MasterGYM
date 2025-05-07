package com.BackEnd.Master.GYM.services.Impl;

import com.BackEnd.Master.GYM.Exceptions.EntityNotFoundException;
import com.BackEnd.Master.GYM.Exceptions.InvalidEntityException;
import com.BackEnd.Master.GYM.entity.TrainingSession;
import com.BackEnd.Master.GYM.repository.TrainingSessionRepo;
import com.BackEnd.Master.GYM.services.TrainingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {
    private final TrainingSessionRepo trainingSessionRepo;

    @Override
    public TrainingSession findById(Long id) {
        return trainingSessionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training session not found with ID: " + id));
    }

    @Override
    public List<TrainingSession> findAll() {
        return trainingSessionRepo.findAll();
    }

    @Override
    public List<TrainingSession> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return trainingSessionRepo.findByDateBetween(startDate, endDate);
    }

    @Override
    public TrainingSession create(TrainingSession entity) {
        validateSession(entity);
        checkTimeConflict(entity);
        return trainingSessionRepo.save(entity);
    }

    @Override
    public TrainingSession update(TrainingSession entity) {
        validateSession(entity);
        checkTimeConflict(entity, entity.getId());
        return trainingSessionRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        trainingSessionRepo.deleteById(id);
    }

    private void validateSession(TrainingSession session) {
        if (session.getStartTime().isAfter(session.getEndTime())) {
            throw new InvalidEntityException("End time must be after start time");
        }
        if (session.getSessionType() == TrainingSession.SessionType.INDIVIDUAL && session.getMaxParticipants() > 1) {
            throw new InvalidEntityException("Individual sessions can't have more than 1 participant");
        }
    }

    private void checkTimeConflict(TrainingSession session) {
        checkTimeConflict(session, null);
    }

    private void checkTimeConflict(TrainingSession session, Long excludeId) {
        List<TrainingSession> existingSessions = trainingSessionRepo.findByDate(session.getDate());
        
        boolean hasConflict = existingSessions.stream()
                .filter(s -> excludeId == null || !s.getId().equals(excludeId))
                .anyMatch(s -> isTimeConflict(
                        session.getStartTime(), session.getEndTime(),
                        s.getStartTime(), s.getEndTime()
                ));
        
        if (hasConflict) {
            throw new InvalidEntityException("Time conflict with existing session");
        }
    }

    private boolean isTimeConflict(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
    
    @Override
    public long countByDate(LocalDate date) {
        return trainingSessionRepo.countByDate(LocalDate.now());
    }
    
    @Override
    public long countActiveSessions(LocalDate date, LocalTime currentTime) {
        return trainingSessionRepo.countActiveSessions(LocalDate.now(), LocalTime.now());
    }




}