package com.pots.admin.model.vo;

import com.pots.admin.model.dto.AdminGameI18nDTO;
import com.pots.admin.model.dto.AdminGameResDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AdminGameDetailVO {
    
    private Long          id;
    private Long      sortOrder;
    private LocalDate releaseDate;
    
    List<AdminGameI18nDTO> i18nArr;
    List<AdminGameResDTO>  resArr;
    
    
}
