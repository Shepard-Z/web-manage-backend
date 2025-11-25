package com.pots.admin.model.vo;

import com.pots.admin.model.dto.AdminUserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AdminUserVO extends AdminUserDTO {
    
    private String token;
    
}
