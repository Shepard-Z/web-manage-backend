package com.pots.site.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GameMainDTO {
    
    private Long      id;           // not null
    private LocalDate releaseDate;
    
    private String    name;         // not null
    private String    category;
    private String    introduction;
    private String    projectType;
    private String    releaseStatus;
    
    private String    imgUrl;
    
}
