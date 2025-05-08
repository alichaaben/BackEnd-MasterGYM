package com.BackEnd.Master.GYM.controller;

import com.BackEnd.Master.GYM.dto.TrainingSessionDto;
import com.BackEnd.Master.GYM.entity.TrainingSession;
import com.BackEnd.Master.GYM.Mapper.TrainingSessionMapper;
import com.BackEnd.Master.GYM.services.TrainingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/training-sessions")
@CrossOrigin("*")
public class TrainingSessionController {
    private final TrainingSessionService trainingSessionService;
    private final TrainingSessionMapper trainingSessionMapper;


    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDto> findById(@PathVariable Long id) {
        TrainingSession entity = trainingSessionService.findById(id);
        return ResponseEntity.ok(trainingSessionMapper.map(entity));
    }

    @GetMapping
    public ResponseEntity<List<TrainingSessionDto>> findAll() {
        List<TrainingSession> entities = trainingSessionService.findAll();
        return ResponseEntity.ok(trainingSessionMapper.map(entities));
    }

    @GetMapping("/range")
    public ResponseEntity<List<TrainingSessionDto>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<TrainingSession> entities = trainingSessionService.findByDateRange(start, end);
        return ResponseEntity.ok(trainingSessionMapper.map(entities));
    }


    @GetMapping("/count/today")
    public long getTotalSessionsToday(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return trainingSessionService.countByDate(date);
    }
    

    @GetMapping("/count/active")
    public long getActiveSessionsCount(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalTime date2
    ) {
        return trainingSessionService.countActiveSessions(date, date2);
    }


    @PostMapping
    public ResponseEntity<TrainingSessionDto> create(@RequestBody TrainingSessionDto dto) {
        TrainingSession entity = trainingSessionMapper.unMap(dto);
        TrainingSession created = trainingSessionService.create(entity);
        return ResponseEntity.ok(trainingSessionMapper.map(created));
    }

    @PutMapping
    public ResponseEntity<TrainingSessionDto> update(@RequestBody TrainingSessionDto dto) {
        TrainingSession current = trainingSessionService.findById(dto.getId());
        trainingSessionMapper.updateEntityFromDto(current, dto);
        TrainingSession updated = trainingSessionService.update(current);
        return ResponseEntity.ok(trainingSessionMapper.map(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        trainingSessionService.delete(id);
        return ResponseEntity.ok().build();
    }
}