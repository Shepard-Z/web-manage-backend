package com.pots.admin.model.dto;

import lombok.Data;

@Data
public class AdminGameResDTO {
    
    private Long   id;
    private Long   gameId;
    private String resourceType;
    private String resLangCode;
    private String resSortOrder;
    private String url;
    
}
