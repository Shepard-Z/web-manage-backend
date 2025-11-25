package com.pots.site.model.dto;

import lombok.Data;

@Data
public class CharaForGameDTO {
    
    private Long   id;           // not null
    
    private String name;         // not null
    
    private String imgUrl;
    
}
