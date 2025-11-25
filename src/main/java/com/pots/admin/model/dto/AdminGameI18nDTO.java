package com.pots.admin.model.dto;

import lombok.Data;

@Data
public class AdminGameI18nDTO {
    
    private Long   id;
    private Long   gameId;
    private String langCode;
    private String name;
    private String category;
    private String introduction;
    private String description;
    private String series;
    private String projectType;
    private String releaseStatus;
    
}
