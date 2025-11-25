package com.pots.admin.model.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminGameSimpleVO {
    
    private Long      id;
    private Long      sortOrder;
    private LocalDate releaseDate;
    
    private String langCode;
    private String name;
    private String category;
    private String introduction;
    private String projectType;
    private String releaseStatus;
    
    private String imgUrl;
    
}
