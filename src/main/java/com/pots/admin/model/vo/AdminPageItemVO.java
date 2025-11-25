package com.pots.admin.model.vo;

import com.pots.admin.model.dto.AdminPageItemDTO;
import com.pots.admin.model.dto.AdminPageItemI18nDTO;
import com.pots.admin.model.dto.AdminPageItemResourceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AdminPageItemVO extends AdminPageItemDTO {
    
    private List<AdminPageItemI18nDTO>     i18nArr;
    private List<AdminPageItemResourceDTO> resourceArr;
    
}
