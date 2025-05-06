package com.BackEnd.Master.GYM.services;

import com.BackEnd.Master.GYM.entity.ContactMessage;
import java.util.List;

public interface ContactMessageService {
    ContactMessage findById(Long id);
    List<ContactMessage> findAll();
    List<ContactMessage> findByStatus(String status);
    List<ContactMessage> searchMessages(String query);
    List<ContactMessage> searchMessagesByStatus(String status, String query);
    ContactMessage create(ContactMessage entity);
    ContactMessage updateStatus(Long id, String status);
    void deleteById(Long id);
    List<ContactMessage> getApprovedMessages();
}