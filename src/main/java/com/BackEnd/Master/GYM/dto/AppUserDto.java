package com.BackEnd.Master.GYM.dto;

import lombok.Data;

@Data
public class AppUserDto {

    private Long id;
    private String userName;
    private String email;
    private String telephone;
    private String motDePasse;
    private String roleName;
    private String profileImage;
    private String description;

}
