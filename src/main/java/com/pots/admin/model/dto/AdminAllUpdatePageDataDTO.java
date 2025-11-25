package com.pots.admin.model.dto;

import com.pots.admin.model.ro.RemoveDataRO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 分類されたフロントからのデータ
@Data
public class AdminAllUpdatePageDataDTO {
    
    private List<AdminPageItemDTO>         newPageItem     = new ArrayList<>();
    private List<AdminPageItemI18nDTO>     newPageItemI18n = new ArrayList<>();
    private List<AdminPageItemResourceDTO> newPageItemRes  = new ArrayList<>();
    
    private List<AdminPageItemI18nDTO>     modifyPageItemI18n = new ArrayList<>();
    private List<AdminPageItemResourceDTO> modifyPageItemRes  = new ArrayList<>();
    
    private List<RemoveDataRO> deletePageItem     = new ArrayList<>();
    private List<RemoveDataRO> deletePageItemI18n = new ArrayList<>();
    private List<RemoveDataRO> deletePageItemRes  = new ArrayList<>();
    
    private List<AdminPageItemResourceDTO> deleteFiles = new ArrayList<>();
    
}
