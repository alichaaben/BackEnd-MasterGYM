package com.BackEnd.Master.GYM.controller;

import com.BackEnd.Master.GYM.dto.ContactMessageDto;
import com.BackEnd.Master.GYM.entity.ContactMessage;
import com.BackEnd.Master.GYM.Mapper.ContactMessageMapper;
import com.BackEnd.Master.GYM.services.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact-messages")
@CrossOrigin("*")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;
    private final ContactMessageMapper contactMessageMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessageDto> findById(@PathVariable Long id) {
        ContactMessage entity = contactMessageService.findById(id);
        return ResponseEntity.ok(contactMessageMapper.map(entity));
    }

    @GetMapping
    public ResponseEntity<List<ContactMessageDto>> findAll() {
        List<ContactMessage> entities = contactMessageService.findAll();
        return ResponseEntity.ok(contactMessageMapper.map(entities));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ContactMessageDto>> findByStatus(@PathVariable String status) {
        List<ContactMessage> entities = contactMessageService.findByStatus(status);
        return ResponseEntity.ok(contactMessageMapper.map(entities));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactMessageDto>> searchMessages(@RequestParam String query) {
        List<ContactMessage> entities = contactMessageService.searchMessages(query);
        return ResponseEntity.ok(contactMessageMapper.map(entities));
    }

    @GetMapping("/search-status")
    public ResponseEntity<List<ContactMessageDto>> searchMessagesByStatus(
            @RequestParam String status,
            @RequestParam String query) {
        List<ContactMessage> entities = contactMessageService.searchMessagesByStatus(status, query);
        return ResponseEntity.ok(contactMessageMapper.map(entities));
    }

    @PostMapping
    public ResponseEntity<ContactMessageDto> create(@RequestBody ContactMessageDto dto) {
        ContactMessage entity = contactMessageMapper.unMap(dto);
        ContactMessage created = contactMessageService.create(entity);
        return ResponseEntity.ok(contactMessageMapper.map(created));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ContactMessageDto> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        
        ContactMessage updated = contactMessageService.updateStatus(id, status.toLowerCase());
        return ResponseEntity.ok(contactMessageMapper.map(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        contactMessageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ContactMessageDto>> getApprovedMessages() {
        List<ContactMessage> entities = contactMessageService.getApprovedMessages();
        return ResponseEntity.ok(contactMessageMapper.map(entities));
    }
}