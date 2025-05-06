package com.BackEnd.Master.GYM.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PhotoDto {
    private Long id;
    private String name;
    private String imageName;
    private String description;
    private LocalDateTime uploadDate;
    private Long albumId;
}