package com.pots.site.model.dto;

import lombok.Data;

@Data
public class PageItemDTO {
    
    private Long   id;              // not null
    private String itemCategory;    // not null
    
    private String title;
    private String subTitle;
    private String introduction;
    
}
