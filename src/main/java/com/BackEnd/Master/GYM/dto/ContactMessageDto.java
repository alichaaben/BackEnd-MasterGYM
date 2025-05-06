package com.BackEnd.Master.GYM.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ContactMessageDto {
    private Long id;
    private String name;
    private String email;
    private String message;
    private String status;
    private LocalDate createdAt;
}