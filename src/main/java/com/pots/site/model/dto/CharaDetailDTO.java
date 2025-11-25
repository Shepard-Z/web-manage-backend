package com.pots.site.model.dto;

import lombok.Data;

@Data
public class CharaDetailDTO {
    
    private Long    id;              // not null
    private Integer age;
    
    private String  name;            // not null
    private String  gender;
    private String  race;
    private String  charaClass;
    private String  introduction;
    
    private String  imgUrl;
}
