package com.BackEnd.Master.GYM.services.Impl;

import com.BackEnd.Master.GYM.Exceptions.EntityNotFoundException;
import com.BackEnd.Master.GYM.Exceptions.InvalidEntityException;
import com.BackEnd.Master.GYM.entity.ContactMessage;
import com.BackEnd.Master.GYM.entity.MessageStatus;
import com.BackEnd.Master.GYM.repository.ContactMessageRepo;
import com.BackEnd.Master.GYM.services.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepo contactMessageRepo;

    @Override
    public ContactMessage findById(Long id) {
        return contactMessageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
    }

    @Override
    public List<ContactMessage> findAll() {
        return contactMessageRepo.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<ContactMessage> findByStatus(String status) {
        return contactMessageRepo.findByStatus(MessageStatus.valueOf(status.toUpperCase()));
    }

    @Override
    public List<ContactMessage> searchMessages(String query) {
        return contactMessageRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMessageContainingIgnoreCase(
                query, query, query);
    }

    @Override
    public List<ContactMessage> searchMessagesByStatus(String status, String query) {
        MessageStatus messageStatus = MessageStatus.valueOf(status.toUpperCase());
        return contactMessageRepo
                .findByStatusAndNameContainingIgnoreCaseOrStatusAndEmailContainingIgnoreCaseOrStatusAndMessageContainingIgnoreCase(
                        messageStatus, query,
                        messageStatus, query,
                        messageStatus, query);
    }

    @Override
    public ContactMessage create(ContactMessage entity) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new InvalidEntityException("Name cannot be empty");
        }
        if (entity.getEmail() == null || entity.getEmail().isEmpty()) {
            throw new InvalidEntityException("Email cannot be empty");
        }
        if (entity.getMessage() == null || entity.getMessage().isEmpty()) {
            throw new InvalidEntityException("Message cannot be empty");
        }
        entity.setStatus(MessageStatus.PENDING);
        return contactMessageRepo.save(entity);
    }

    @Override
    public ContactMessage updateStatus(Long id, String status) {
        ContactMessage message = contactMessageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
        
        message.setStatus(MessageStatus.fromString(status));
        return contactMessageRepo.save(message);
    }

    @Override
    public void deleteById(Long id) {
        contactMessageRepo.deleteById(id);
    }

    @Override
    public List<ContactMessage> getApprovedMessages() {
        return contactMessageRepo.findByStatus(MessageStatus.APPROVED);
    }
}