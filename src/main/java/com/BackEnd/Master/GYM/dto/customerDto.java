package com.BackEnd.Master.GYM.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class customerDto {
    private Long id;
    private String userName;
    private String email;
    private String telephone;
    private String pack;
    private String profileImage;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long userId;
    private String montPay;

}
