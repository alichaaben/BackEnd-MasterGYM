package com.BackEnd.Master.GYM.repository;

import com.BackEnd.Master.GYM.entity.ContactMessage;
import com.BackEnd.Master.GYM.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepo extends JpaRepository<ContactMessage, Long> {
        List<ContactMessage> findByStatus(MessageStatus status);
        List<ContactMessage> findAllByOrderByCreatedAtDesc();
        

        List<ContactMessage> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMessageContainingIgnoreCase(
                        String name, String email, String message);

        List<ContactMessage> findByStatusAndNameContainingIgnoreCaseOrStatusAndEmailContainingIgnoreCaseOrStatusAndMessageContainingIgnoreCase(
                        MessageStatus status1, String name,
                        MessageStatus status2, String email,
                        MessageStatus status3, String message);


}