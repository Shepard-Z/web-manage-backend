package com.pots.site.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GameDetailDTO {
    
    private Long      id;           // not null
    private LocalDate releaseDate;
    
    private String    name;         // not null
    private String    category;
    private String    introduction;
    private String    description;
    private String    series;
    private String    projectType;
    private String    releaseStatus;
    
}
