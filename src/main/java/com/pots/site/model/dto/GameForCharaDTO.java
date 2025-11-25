package com.pots.site.model.dto;

import lombok.Data;

@Data
public class GameForCharaDTO {
    
    private Long    id;           // not null
    private Integer sortOrder;
    
    private String  name;         // not null
    
    private String  imgUrl;
    
}
