package com.pots.admin.model.dto;

import com.pots.common.model.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AdminUserDTO extends EntityBase {
    
    private Long   id;
    private String name;
    private String gender;
    private String introduction;
    private String imgUrl;
    private String passwordHash;
    
}
